package pp.ua.lifebook.web.controllers;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseController {
    public static final String OK = "ok";

    /**
     * Writes to the response default ok message.
     * @throws IOException if failed to write into response
     */
    protected void ok(HttpServletResponse response) throws IOException {
        response.getWriter().write(OK);
    }
}
