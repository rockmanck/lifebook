package ua.lifebook.web.controllers;

import ua.lifebook.admin.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseController {
    public static String OK = "ok";

    protected User user(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    protected void ok(HttpServletResponse response) throws IOException {
        response.getWriter().write(OK);
    }
}
