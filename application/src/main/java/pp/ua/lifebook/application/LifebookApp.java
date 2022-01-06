package pp.ua.lifebook.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import pp.ua.lifebook.application.config.AppConfig;
import pp.ua.lifebook.web.config.WebConfig;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LifebookApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(LifebookApp.class, AppConfig.class, WebConfig.class)
            .application()
            .run(args);
    }
}