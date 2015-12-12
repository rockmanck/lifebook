package ua.lifebook.web.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ua.lifebook.config.AppConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {
    @Override public void onStartup(ServletContext container) throws ServletException {
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
        dispatcher.addMapping("/*");

        // Add Gateway filter
//        container.addFilter("gateway", GatewayFilter.class).addMappingForUrlPatterns(null, false, "/*");
    }
}
