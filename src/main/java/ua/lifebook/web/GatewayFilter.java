package ua.lifebook.web;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ua.lifebook.admin.Language;
import ua.lifebook.admin.User;
import ua.lifebook.config.AppConfig;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class GatewayFilter implements Filter {
    private final String encoding = "utf8";
    private Authorization authorization;
    private static final List<Predicate<String>> rulesAllowedLinks = new ArrayList<>();
    static {
        rulesAllowedLinks.add(e -> e.startsWith("/css/"));
        rulesAllowedLinks.add(e -> e.startsWith("/img/"));
        rulesAllowedLinks.add(e -> e.startsWith("/js/"));
    }

    private static final boolean devMode = AppConfig.config.getBoolean("devMode");
    private static final Map<Language, ResourceBundle> bundles = new HashMap<>();

    @Override public void init(FilterConfig config) throws ServletException {
        final ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        authorization = applicationContext.getBean(Authorization.class);
    }

    @Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        setCharacterEncoding(servletRequest, servletResponse);
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String requestURI = request.getRequestURI();

        if (isAllowed(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        final User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            if (isNewLogin(request)) {
                if (requestURI.contains("login")) response.sendRedirect("/");
                else chain.doFilter(request, response);
                return;
            }

            setLocale(request, Language.EN);
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }

        setLocale(request, user.getLanguage());

        if (requestURI.startsWith("/admin") && !user.isAdmin()) {
            response.sendRedirect("/");
            return;
        }

        chain.doFilter(request, response);
    }

    private void setLocale(HttpServletRequest request, Language language) throws UnsupportedEncodingException {
        if (!bundles.containsKey(language) || devMode) {
            bundles.put(language, ResourceBundle.getBundle("MessagesBundle", language.getLocale(), language.getEncodingControl()));
        }
        request.getSession().setAttribute("i18n", bundles.get(language));
    }

    private boolean isNewLogin(HttpServletRequest request) {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        final User user = authorization.login(login, password);
        if (user == null) return false;

        request.getSession().setAttribute("user", user);
        return true;
    }

    @Override public void destroy() {

    }

    private boolean isAllowed(String uri) {
        for (Predicate<String> rule : rulesAllowedLinks) {
            if (rule.test(uri)) return true;
        }
        return false;
    }

    private void setCharacterEncoding(ServletRequest request, ServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
    }
}
