package ua.lifebook.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ua.lifebook.admin.User;
import ua.lifebook.db.LifeBookJdbc;

public class Authorization {
    private final LifeBookJdbc jdbc;

    @Autowired
    public Authorization(LifeBookJdbc jdbc) {
        this.jdbc = jdbc;
    }

    public User login(String login, String password) {
        if (StringUtils.isEmpty(login) && StringUtils.isEmpty(password)) return null;
        return jdbc.loginUser(login, password);
    }
}
