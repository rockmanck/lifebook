package ua.lifebook.web.controllers;

import pp.ua.lifebook.user.User;
import ua.lifebook.web.utils.SessionKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseController {
    public static final String OK = "ok";

    protected User user(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(SessionKeys.USER);
    }

    /**
     * Writes to the response default ok message.
     * @throws IOException if failed to write into response
     */
    protected void ok(HttpServletResponse response) throws IOException {
        response.getWriter().write(OK);
    }
}
