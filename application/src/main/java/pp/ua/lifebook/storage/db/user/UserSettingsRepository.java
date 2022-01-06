package pp.ua.lifebook.storage.db.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pp.ua.lifebook.user.parameters.DefaultTab;
import pp.ua.lifebook.user.parameters.UserSettings;
import pp.ua.lifebook.user.parameters.ViewOption;

import javax.sql.DataSource;

@Repository
public class UserSettingsRepository {
    private final JdbcTemplate jdbc;

    public UserSettingsRepository(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    public UserSettings get(int userId) {
        final UserSettings result = new UserSettings();
        jdbc.query("select view_options, default_tab from user_settings where user_id = ?", rs -> {
            final String view_options = rs.getString("view_options");
            result.setViewOptions(ViewOption.parse(view_options));
            result.setDefaultTab(DefaultTab.byName(rs.getString("default_tab")));
        }, userId);
        return result;
    }
}
