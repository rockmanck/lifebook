package ua.lifebook.db;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.lifebook.admin.Language;
import ua.lifebook.admin.User;
import ua.lifebook.db.sqlbuilder.DynamicSqlBuilder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersJdbc extends JdbcTemplate {
    private final DynamicSqlBuilder sqlBuilder = new DynamicSqlBuilder();

    public UsersJdbc(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Tries to find user with such credentials. If not found returns null.
     */
    public User loginUser(String login, String password) {
        final String sql = sqlBuilder.sql("GetUserId")
            .param("login", login)
            .param("password", password)
            .build();
        try {
            final Integer id = queryForObject(sql, ((rs, rowNum) -> rs.getInt("Id")));
            return id > 0 ? getUser(id) : null;
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUser(int id) {
        return queryForObject("SELECT id AS userId, * FROM users WHERE id = " + id, ((rs, rowNum) -> getUser(rs)));
    }

    private User getUser(ResultSet rs) throws SQLException {
        final User user = new User();
        user.setId(rs.getInt("userId"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setAdmin(rs.getBoolean("is_admin"));
        user.setPassword(rs.getString("password"));
        final String lang = rs.getString("language");
        user.setLanguage(!StringUtils.isEmpty(lang) ? Language.byCode(lang) : Language.EN);
        return user;
    }
}
