package ua.lifebook.web.config;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ua.lifebook.config.AppConfig;
import ua.lifebook.config.DbConfig;
import ua.lifebook.web.GatewayFilter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(WebAppInitializer.class);

    @Override public void onStartup(ServletContext container) throws ServletException {
        logger.info("Running Flyway migrations");
        runFlywayMigration();
        container.setInitParameter("contextInitializerClasses", "ua.lifebook.config.WebConfigPropertySourceInitializer");

        // Create the 'root' Spring application context
        final AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));
        container.addListener(new SessionListener());

        rootContext.setDisplayName("LifeBook Context");

        // Create the dispatcher servlet's Spring application context
        final AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
        dispatcherServlet.register(WebConfig.class);

        // Register and map the dispatcher servlet
        final ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("*.html");

        // Add Gateway filter
        container.addFilter("gateway", GatewayFilter.class).addMappingForUrlPatterns(null, false, "/*");
    }

    private void runFlywayMigration() {
        final Flyway flyway = new Flyway();
        flyway.setDataSource(DbConfig.dataSource());
        flyway.migrate();
    }
}
