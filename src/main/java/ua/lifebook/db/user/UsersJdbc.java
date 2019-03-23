package ua.lifebook.db.user;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.lifebook.db.sqlbuilder.DynamicSqlBuilder;
import ua.lifebook.user.User;
import ua.lifebook.user.parameters.DefaultTab;
import ua.lifebook.user.parameters.Language;
import ua.lifebook.user.parameters.UserSettings;
import ua.lifebook.user.parameters.ViewOption;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

class UsersJdbc extends JdbcTemplate {
    private final Logger usersJdbcLogger = LoggerFactory.getLogger(this.getClass());
    private final DynamicSqlBuilder sqlBuilder = new DynamicSqlBuilder();

    UsersJdbc(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Tries to find user with such credentials. If not found returns null.
     */
    User getUserByCreds(String login, String password) {
        final String sql = sqlBuilder.sql("GetUserId")
            .param("login", login)
            .param("password", password)
            .build();
        try {
            final Integer id = queryForObject(sql, Integer.class);
            return id != null && id > 0 ? getUser(id) : null;
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            usersJdbcLogger.error("Failed to get user by credentials for " + login + " login", e);
            return null;
        }
    }

    void updateUserSettings(String options, User user) {
        final String defaultTab = user.getUserSettings().getDefaultTab().name();
        update("UPDATE user_settings SET view_options = ?, default_tab = ? WHERE user_id = ?", options, defaultTab, user.getId());
    }

    private User getUser(int id) {
        final String sql = sqlBuilder.sql("GetUser")
            .param("id", id)
            .build();
        return queryForObject(sql, ((rs, rowNum) -> getUser(rs)));
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
        userSettings.setDefaultTab(DefaultTab.byName(rs.getString("default_tab")));
        user.setUserSettings(userSettings);
        final String lang = rs.getString("language");
        user.setLanguage(!StringUtils.isEmpty(lang) ? Language.byCode(lang) : Language.EN);
        return user;
    }
}
