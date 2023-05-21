package pp.ua.lifebook.db.config;

import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.ua.lifebook.db.config.flyway.FlywayAction;
import pp.ua.lifebook.db.moment.MomentDbStorage;
import pp.ua.lifebook.db.plan.PlansDbStorage;
import pp.ua.lifebook.db.user.UsersDbStorage;
import pp.ua.lifebook.moments.MomentStorage;
import pp.ua.lifebook.plan.port.PlansStoragePort;
import pp.ua.lifebook.user.UsersStorage;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Bean
    public Flyway flyway(@Value("${lb.db.flyway}") String action, DataSource dataSource) {
        final Flyway flyway = Flyway.configure()
            .dataSource(dataSource)
            .outOfOrder(true)
            .load();
        FlywayAction.valueOf(action.toUpperCase()).execute(flyway);
        return null;
    }

    @Bean
    DSLContext dSLContext(DataSource dataSource) {
        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Bean
    public UsersStorage usersJdbc(DataSource dataSource, DSLContext dslContext) {
        return new UsersDbStorage(dataSource, dslContext);
    }

    @Bean
    public PlansStoragePort plansJdbc(DataSource dataSource) {
        return new PlansDbStorage(dataSource);
    }

    @Bean
    public MomentStorage momentStorage(DataSource dataSource) {
        return new MomentDbStorage(dataSource);
    }
}
