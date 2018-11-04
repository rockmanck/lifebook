package ua.lifebook.application;

import com.typesafe.config.Config;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import ua.lifebook.application.flyway.FlywaySync;
import ua.lifebook.config.AppConfig;
import ua.lifebook.config.ConfigurationHolder;
import ua.lifebook.web.config.WebConfig;

import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LifebookApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        FlywaySync.sync();
        final Map<String, Object> properties = readProperties();
        new SpringApplicationBuilder(LifebookApp.class, AppConfig.class, WebConfig.class)
            .properties(properties)
            .application()
            .run(args);
    }

    private static Map<String, Object> readProperties() {
        Config config = new ConfigurationHolder().getConfig();
        final Map<String, Object> properties = config
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().unwrapped()));
        overrideServerPortProperty(properties);
        return properties;
    }

    private static void overrideServerPortProperty(Map<String, Object> properties) {
        properties.put("server.port", properties.get("lb.server.port"));
    }
}
