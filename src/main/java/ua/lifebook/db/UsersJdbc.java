package ua.lifebook.db;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.lifebook.db.sqlbuilder.DynamicSqlBuilder;
import ua.lifebook.users.User;
import ua.lifebook.users.parameters.DefaultTab;
import ua.lifebook.users.parameters.Language;
import ua.lifebook.users.parameters.UserSettings;
import ua.lifebook.users.parameters.ViewOption;

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
    public User getUserByCreds(String login, String password) {
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
        final String sql = sqlBuilder.sql("GetUser")
            .param("id", id)
            .build();
        return queryForObject(sql, ((rs, rowNum) -> getUser(rs)));
    }

    public void updateUserSettings(String options, User user) {
        final String defaultTab = user.getUserSettings().getDefaultTab().name();
        update("UPDATE user_settings SET view_options = ?, default_tab = ? WHERE user_id = ?", options, defaultTab, user.getId());
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
        final UserSettings userSettings = new UserSettings();
        userSettings.setViewOptions(ViewOption.parse(rs.getString("view_options")));
        userSettings.setDefaultTab(DefaultTab.valueOf(rs.getString("default_tab")));
        user.setUserSettings(userSettings);
        final String lang = rs.getString("language");
        user.setLanguage(!StringUtils.isEmpty(lang) ? Language.byCode(lang) : Language.EN);
        return user;
    }
}
