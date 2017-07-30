package ua.lifebook.db;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.lifebook.db.repository.Repository;
import ua.lifebook.db.sqlbuilder.DynamicSqlBuilder;
import ua.lifebook.plans.Category;
import ua.lifebook.plans.Plan;
import ua.lifebook.plans.PlanStatus;
import ua.lifebook.plans.RepeatType;
import ua.lifebook.users.User;
import ua.lifebook.users.parameters.ViewOption;
import ua.lifebook.utils.DateUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class PlansServiceImpl extends JdbcTemplate implements PlansService {
    private final DynamicSqlBuilder sqlBuilder = new DynamicSqlBuilder();
    private final Repository repository = new Repository(this);

    public PlansServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override public void savePlan(Plan plan) {
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
        update(sql, values, new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP});
        //repository.save(plan);
    }

    @Override public Plan getPlan(Integer id) {
        final String sql = sqlBuilder.sql("GetPlan").build();
        try {
            return queryForObject(sql, (rs, rowNum) -> plan(rs), id);
        } catch (EmptyResultDataAccessException e) {
            return new Plan();
        }
    }

    @Override public List<Plan> getPlans(Date start, Date end, User user, Set<ViewOption> viewOptions) {
        final String sql = sqlBuilder.sql("GetPlans")
            .param("startDate", DateUtils.format(start))
            .param("endDate", DateUtils.format(end))
            .param("userId", user.getId())
            .param("show_outdated", viewOptions.contains(ViewOption.SHOW_OUTDATED))
            .param("show_done", viewOptions.contains(ViewOption.SHOW_DONE))
            .param("show_canceled", viewOptions.contains(ViewOption.SHOW_CANCELED))
            .build();

        final List<Plan> result = new ArrayList<>();
        query(sql, rs -> {
            final Plan plan = plan(rs);
            plan.setUser(user);
            result.add(plan);
        });
        result.sort(Comparator.comparing(Plan::getDueDate));
        return result;
    }

    @Override public void updatePlanStatus(int planId, PlanStatus status) {
        update("UPDATE plans SET status = ? WHERE id = ?", status.getCode(), planId);
    }

    private Plan plan(ResultSet rs) throws SQLException {
        final Plan plan = new Plan();
        plan.setId(rs.getInt("plan_id"));
        plan.setCategory(category(rs));
        plan.setTitle(rs.getString("title"));
        plan.setComments(rs.getString("comments"));
        plan.setStatus(PlanStatus.byCode(rs.getString("status")));
        final Timestamp due_time = rs.getTimestamp("due_time");
        plan.setDueDate(DateUtils.dateToLocalDateTime(due_time));
        plan.setRepeated(RepeatType.byCode(rs.getString("repeated")));
        plan.setOutdated(rs.getBoolean("outdated"));
        return plan;
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
