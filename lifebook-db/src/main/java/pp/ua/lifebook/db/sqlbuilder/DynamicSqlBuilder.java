package pp.ua.lifebook.db.sqlbuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DynamicSqlBuilder {
    private final VelocityManager velocityManager;
    private final CallerResolver callerResolver;

    public DynamicSqlBuilder() {
        this.velocityManager = new VelocityManager();
        callerResolver = new CallerResolver();
    }

    public Builder sql(String templateName, Object caller) {
        final Class<?> callerClass = caller instanceof Class<?> ? (Class<?>) caller : caller.getClass();
        return new Builder(templateName, callerClass);
    }

    public Builder sql(String templateName, Class<?> aClass) {
        return new Builder(templateName, aClass);
    }

    public Builder sql(String templateName) {
        try {
            return sql(templateName, callerResolver.getCaller(DynamicSqlBuilder.class));
        } catch (ClassNotFoundException e) {
            throw new NoSuchClassException(e);
        }
    }

    public class Builder {
        private final String templatePath;
        private final Map<String, Object> context = new HashMap<>();

        Builder(String templateName, Class<?> callerClass){
            final Package aPackage = callerClass.getPackage();
            final String path = aPackage.getName().replace(".", File.separator);
            this.templatePath = path + File.separator + templateName + ".sql.vm";
        }

        public Builder param(String name, Object value) {
            context.put(name, value);
            return this;
        }

        public String build() {
            return velocityManager.renderTemplate(templatePath, context);
        }
    }

    private static class NoSuchClassException extends RuntimeException {
        NoSuchClassException(Exception e) {
            super(e);
        }
    }
}
