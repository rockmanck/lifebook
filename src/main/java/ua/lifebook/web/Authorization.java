package ua.lifebook.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ua.lifebook.db.UsersJdbc;
import ua.lifebook.users.User;

public class Authorization {
    private final UsersJdbc jdbc;

    @Autowired
    public Authorization(UsersJdbc jdbc) {
        this.jdbc = jdbc;
    }

    public User login(String login, String password) {
        if (StringUtils.isEmpty(login) && StringUtils.isEmpty(password)) return null;
        return jdbc.loginUser(login, password);
    }
}
