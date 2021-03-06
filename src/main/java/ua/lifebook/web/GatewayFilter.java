package ua.lifebook.web;

import org.apache.commons.codec.Charsets;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import pp.ua.lifebook.db.user.UsersDbStorage;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.UsersStorage;
import pp.ua.lifebook.user.parameters.Language;
import ua.lifebook.i18n.EncodingControl;
import ua.lifebook.web.application.config.AppConfig;
import ua.lifebook.web.utils.SessionKeys;

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
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class GatewayFilter implements Filter {
    private UsersStorage usersStorage;
    private static final List<Predicate<String>> rulesAllowedLinks = new ArrayList<>();
    static {
        rulesAllowedLinks.add(e -> e.startsWith("/css/"));
        rulesAllowedLinks.add(e -> e.startsWith("/img/"));
        rulesAllowedLinks.add(e -> e.startsWith("/js/"));
    }

    private static final boolean DEV_MODE = AppConfig.config.getBoolean("devMode");
    private static final EncodingControl ENCODING_CONTROL = new EncodingControl(Charsets.UTF_8);
    private static final Map<Language, ResourceBundle> bundles = new EnumMap<>(Language.class);

    @Override public void init(FilterConfig config) {
        final ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        usersStorage = applicationContext.getBean(UsersStorage.class);
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

        final User user = (User) request.getSession().getAttribute(SessionKeys.USER);
        if (user == null) {
            if (isAuthorized(request)) {
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

    private void setLocale(HttpServletRequest request, Language language) {
        if (!bundles.containsKey(language) || DEV_MODE) {
            bundles.put(language, ResourceBundle.getBundle("MessagesBundle", language.getLocale(), ENCODING_CONTROL));
        }
        request.getSession().setAttribute("i18n", bundles.get(language));
    }

    private boolean isAuthorized(HttpServletRequest request) {
        try {
            final User user = tryGetUser(request);
            request.getSession().setAttribute("user", user);
            return true;
        } catch (UsersDbStorage.NoSuchUser | UsersDbStorage.EmptyLogin e) {
            return false;
        }
    }

    private User tryGetUser(HttpServletRequest request) {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        return usersStorage.getUser(login, password);
    }

    @Override
    public void destroy() {
        // nothing to do
    }

    private boolean isAllowed(String uri) {
        for (Predicate<String> rule : rulesAllowedLinks) {
            if (rule.test(uri)) return true;
        }
        return false;
    }

    private void setCharacterEncoding(ServletRequest request, ServletResponse response) throws UnsupportedEncodingException {
        String encoding = "utf8";
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
    }
}
