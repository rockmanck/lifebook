package pp.ua.lifebook.db.config.flyway;

import org.flywaydb.core.Flyway;

public enum FlywayAction {
    CLEAN {
        @Override public void execute(Flyway flyway) {
            flyway.clean();
            flyway.migrate();
        }
    },
    BASELINE {
        @Override public void execute(Flyway flyway) {
            flyway.baseline();
            flyway.migrate();
        }
    },
    MIGRATE {
        @Override public void execute(Flyway flyway) {
            flyway.migrate();
        }
    },
    VALIDATE {
        @Override public void execute(Flyway flyway) {
            flyway.validate();
        }
    },
    REPAIR {
        @Override public void execute(Flyway flyway) {
            flyway.repair();
        }
    };

    public abstract void execute(Flyway flyway);
}