package ua.lifebook.web.application.config;

import org.springframework.core.env.PropertySource;

public class ConfigPropertySource extends PropertySource<Object> {

    @Override
    public Object getProperty(String name) {
        if (name.contains("[")) {
            return null;
        }

        if (name.contains(":")) {
            return null;
        }

        if (AppConfig.config.hasPath(name)) {
            return AppConfig.config.getAnyRef(name);
        }
        return null;
    }

    public ConfigPropertySource(String name) {
        super(name);
    }
}
