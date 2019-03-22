package ua.lifebook.web.application.flyway;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lifebook.web.application.config.AppConfig;
import ua.lifebook.web.application.config.DbConfig;

public final class FlywaySync {
    private static final Logger logger = LoggerFactory.getLogger(FlywaySync.class);

    public static void sync() {
        migrateLifebookPgDb();
    }

    private static void migrateLifebookPgDb() {
        logger.info("Migrate PostgreSQL LifeBook db");
        final Flyway flyway = Flyway.configure()
            .dataSource(DbConfig.dataSource())
            .locations("classpath:db/lifebook")
            .outOfOrder(true)
            .load();
        getFlywayAction().execute(flyway);
    }

    private static FlywayAction getFlywayAction() {
        final String param = AppConfig.config.getString("db.flyway");
        return FlywayAction.valueOf(param.toUpperCase());
    }
}