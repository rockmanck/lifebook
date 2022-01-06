package pp.ua.lifebook.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.UsersStorage;
import pp.ua.lifebook.user.parameters.Language;
import pp.ua.lifebook.web.i18n.EncodingControl;

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
import java.nio.charset.StandardCharsets;
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

    private static final EncodingControl ENCODING_CONTROL = new EncodingControl(StandardCharsets.UTF_8);
    private static final Map<Language, ResourceBundle> bundles = new EnumMap<>(Language.class);

    @Value("${lb.devMode}")
    private boolean devMode;

    @Override public void init(FilterConfig config) {
        final ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        usersStorage = applicationContext.getBean(UsersStorage.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        setCharacterEncoding(servletRequest, servletResponse);
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        setLocale(request, Language.EN); // TODO @AndrewG: move local setup to better place
        chain.doFilter(request, response);
    }

    private void setLocale(HttpServletRequest request, Language language) {
        if (!bundles.containsKey(language) || devMode) {
            bundles.put(language, ResourceBundle.getBundle("MessagesBundle", language.getLocale(), ENCODING_CONTROL));
        }
        request.getSession().setAttribute("i18n", bundles.get(language));
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

    private void setCharacterEncoding(ServletRequest request, ServletResponse response) throws UnsupportedEncodingException {
        String encoding = "utf8";
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
    }
}
