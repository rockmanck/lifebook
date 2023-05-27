package pp.ua.lifebook.db.plan;

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
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class PlansDbStorage implements PlansStoragePort {
    private final DynamicSqlBuilder sqlBuilder = new DynamicSqlBuilder();
    private final JdbcTemplate jdbc;

    public PlansDbStorage(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public void savePlan(Plan plan) {
        final String sql;
        if (plan.getId() == null) {
            sql = "INSERT INTO plans (title, repeated, comments, status, user_id, category, due_time) VALUES (?,?,?,?,?,?,?)";
        } else {
            sql = "UPDATE plans SET title = ?, repeated = ?, comments = ?, status = ?, user_id = ?, category = ?, due_time = ? WHERE Id = " + plan.getId();
        }
        final RepeatType repeated = plan.getRepeated();
        final Category category = plan.getCategory();
        final Timestamp timestamp = new Timestamp(DateUtils.getMillisFromLocalDateTime(plan.getDueDate()));
        final Object[] values = {plan.getTitle(), repeated != null ? repeated.getCode() : null, plan.getComments(),
            plan.getStatus().getCode(), plan.getUser().getId(), category != null ? category.getId() : null, timestamp};
        jdbc.update(sql, values, new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP});
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
}
