package ua.lifebook.db;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.lifebook.admin.User;
import ua.lifebook.db.repository.Repository;
import ua.lifebook.db.sqlbuilder.DynamicSqlBuilder;
import ua.lifebook.plans.Category;
import ua.lifebook.plans.Plan;
import ua.lifebook.plans.PlanStatus;
import ua.lifebook.plans.RepeatType;
import ua.lifebook.utils.DateUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PlansJdbc extends JdbcTemplate {
    private final DynamicSqlBuilder sqlBuilder = new DynamicSqlBuilder();
    private final Repository repository = new Repository(this);

    public PlansJdbc(DataSource dataSource) {
        super(dataSource);
    }

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
        update(sql, values, new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP});
        //repository.save(plan);
    }

    public Plan getPlan(Integer id) {
        final String sql = sqlBuilder.sql("GetPlan").build();
        try {
            return queryForObject(sql, (rs, rowNum) -> plan(rs), id);
        } catch (EmptyResultDataAccessException e) {
            return new Plan();
        }
    }

    /**
     * Returns all plan for specified date and user. Sorts collection by due time.
     */
    public List<Plan> getPlans(Date start, Date end, User user) {
        final String sql = sqlBuilder.sql("GetPlans").build();
        final List<Plan> result = new ArrayList<>();
        query(sql, rs -> {
            final Plan plan = plan(rs);
            plan.setUser(user);
            result.add(plan);
        }, start, end, user.getId());
        Collections.sort(result, (o1, o2) -> o1.getDueDate().compareTo(o2.getDueDate()));
        return result;
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
