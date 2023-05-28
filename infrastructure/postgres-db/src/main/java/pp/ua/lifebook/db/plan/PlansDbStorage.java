package pp.ua.lifebook.db.plan;

import org.jooq.DSLContext;
import org.json.JSONArray;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import pp.ua.lifebook.db.sqlbuilder.DynamicSqlBuilder;
import pp.ua.lifebook.plan.Category;
import pp.ua.lifebook.plan.Plan;
import pp.ua.lifebook.plan.PlanStatus;
import pp.ua.lifebook.plan.RepeatType;
import pp.ua.lifebook.plan.port.PlansStoragePort;
import pp.ua.lifebook.tag.Tag;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.parameters.ViewOption;
import pp.ua.lifebook.utils.DateUtils;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pp.ua.lifebook.storage.db.scheme.Tables.PLANS;
import static pp.ua.lifebook.storage.db.scheme.Tables.TAG;
import static pp.ua.lifebook.storage.db.scheme.Tables.TAG_PLAN_RELATION;

public class PlansDbStorage implements PlansStoragePort {
    private final DynamicSqlBuilder sqlBuilder = new DynamicSqlBuilder();
    private final JdbcTemplate jdbc;
    private final DSLContext dslContext;

    public PlansDbStorage(DataSource dataSource, DSLContext dslContext) {
        this.jdbc = new JdbcTemplate(dataSource);
        this.dslContext = dslContext;
    }

    @Override
    public void savePlan(Plan plan) {
        upsert(plan);
        saveTags(new HashSet<>(plan.getTags()), plan.getUser().getId(), plan.getId());
    }

    @Override
    public Plan getPlan(Integer id, int userId) {
        final String sql = sqlBuilder.sql("GetPlan").build();
        try {
            return jdbc.queryForObject(sql, (rs, rowNum) -> plan(rs, userId), id, id);
        } catch (EmptyResultDataAccessException e) {
            return Plan.builder().createPlan();
        }
    }

    @Override
    public List<Plan> getPlans(LocalDate start, LocalDate end, User user, Set<ViewOption> viewOptions) {
        final String sql = sqlBuilder.sql("GetPlans")
            .param("startDate", DateUtils.format(start))
            .param("endDate", DateUtils.format(end))
            .param("userId", user.getId())
            .param("show_outdated", viewOptions.contains(ViewOption.SHOW_OUTDATED))
            .param("show_done", viewOptions.contains(ViewOption.SHOW_DONE))
            .param("show_canceled", viewOptions.contains(ViewOption.SHOW_CANCELED))
            .build();

        final List<Plan> result = new ArrayList<>();
        jdbc.query(sql, rs -> {
            final Plan plan = plan(rs, user.getId());
            plan.setUser(user);
            result.add(plan);
        });
        result.sort(Comparator.comparing(Plan::getDueDate));
        return result;
    }

    @Override
    public void updatePlanStatus(int planId, PlanStatus status) {
        jdbc.update("UPDATE plans SET status = ? WHERE id = ?", status.getCode(), planId);
    }

    private void upsert(Plan plan) {
        final String repeated = plan.getRepeated() != null ? plan.getRepeated().getCode() : null;
        final Integer category = plan.getCategory() != null ? plan.getCategory().getId() : null;
        if (plan.getId() == null) {
            final int planId = dslContext.insertInto(
                    PLANS,
                    PLANS.TITLE,
                    PLANS.REPEATED,
                    PLANS.COMMENTS,
                    PLANS.STATUS,
                    PLANS.USER_ID,
                    PLANS.CATEGORY,
                    PLANS.DUE_TIME
                )
                .values(
                    plan.getTitle(),
                    repeated,
                    plan.getComments(),
                    plan.getStatus().getCode(),
                    plan.getUser().getId(),
                    category,
                    plan.getDueDate()
                )
                .returningResult(PLANS.ID)
                .fetchOne()
                .getValue(PLANS.ID);

            plan.setId(planId);
        } else {
            dslContext.update(PLANS)
                .set(PLANS.TITLE, plan.getTitle())
                .set(PLANS.REPEATED, repeated)
                .set(PLANS.COMMENTS, plan.getComments())
                .set(PLANS.STATUS, plan.getStatus().getCode())
                .set(PLANS.USER_ID, plan.getUser().getId())
                .set(PLANS.CATEGORY, category)
                .set(PLANS.DUE_TIME, plan.getDueDate())
                .where(PLANS.ID.eq(plan.getId()))
                .execute();
        }
    }

    private Plan plan(ResultSet rs, Integer userId) throws SQLException {
        final Plan plan = Plan.builder().createPlan();
        plan.setId(rs.getInt("plan_id"));
        plan.setCategory(category(rs));
        plan.setTitle(rs.getString("title"));
        plan.setComments(rs.getString("comments"));
        plan.setStatus(PlanStatus.byCode(rs.getString("status")));
        final Timestamp dueTime = rs.getTimestamp("due_time");
        plan.setDueDate(DateUtils.dateToLocalDateTime(dueTime));
        plan.setRepeated(RepeatType.byCode(rs.getString("repeated")));
        plan.setOutdated(rs.getBoolean("outdated"));
        Array rsArray = rs.getArray("tags");
        plan.setTags(toTags(rsArray, userId));
        return plan;
    }

    private List<Tag> toTags(Array array, int userId) throws SQLException {
        if (array == null) {
            return List.of();
        }

        return Stream.of((String[]) array.getArray())
            .map(e -> {
                JSONArray values = new JSONArray(e);
                return new Tag(values.getInt(0), userId, values.getString(1));
            })
            .toList();
    }

    private Category category(ResultSet rs) throws SQLException {
        final int id = rs.getInt("category_id");
        if (id > 0) {
            final Category category = new Category();
            category.setId(id);
            category.setName(rs.getString("category_name"));
            return category;
        } else {
            return null;
        }
    }

    private void saveTags(Set<Tag> tags, Integer userId, Integer planId) {
        Set<Tag> updatedTags = createNewTags(tags, userId);

        // firstly remove to make sure re-added tags during edit will be saved
        updatedTags.stream()
            .filter(Tag::removed)
            .forEach(tag -> dslContext.delete(TAG_PLAN_RELATION)
                .where(TAG_PLAN_RELATION.PLAN_ID.eq(planId).and(TAG_PLAN_RELATION.TAG_ID.eq(tag.id())))
                .execute());

        updatedTags.stream()
            .filter(t -> !t.removed())
            .forEach(tag -> dslContext.insertInto(TAG_PLAN_RELATION, TAG_PLAN_RELATION.PLAN_ID, TAG_PLAN_RELATION.TAG_ID)
                .values(planId, tag.id())
                .onConflictDoNothing()
                .execute());
    }

    private Set<Tag> createNewTags(Set<Tag> tags, Integer userId) {
        return tags.stream()
            .map(t -> {
                if (t.id() == null && !t.removed()) {
                    Integer tagId = dslContext.insertInto(TAG, TAG.NAME, TAG.USER_ID)
                        .values(t.name(), userId)
                        .returningResult(TAG.ID)
                        .fetchOne()
                        .getValue(TAG.ID);
                    return new Tag(tagId, userId, t.name());
                } else {
                    return t;
                }
            }).collect(Collectors.toSet());
    }
}
