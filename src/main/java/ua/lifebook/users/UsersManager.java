package ua.lifebook.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.lifebook.db.UsersJdbc;

@Component
public class UsersManager {
    @Autowired private UsersJdbc usersJdbc;

    /**
     * Updates view option in {@code user} and saves them into database.
     * @param options {@link ViewOption} values joined by ','
     * @param defaultTab
     */
    public void updateSettings(String options, String defaultTab, User user) {
        user.getUserSettings().setViewOptions(ViewOption.parse(options));
        user.getUserSettings().setDefaultTab(DefaultTab.valueOf(defaultTab));
        usersJdbc.updateUserSettings(options, user);
    }
}
