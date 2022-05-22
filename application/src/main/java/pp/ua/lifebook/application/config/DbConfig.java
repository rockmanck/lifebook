package pp.ua.lifebook.application.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pp.ua.lifebook.application.flyway.FlywayAction;
import pp.ua.lifebook.db.moment.MomentDbStorage;
import pp.ua.lifebook.db.plan.PlansDbStorage;
import pp.ua.lifebook.db.user.UsersDbStorage;
import pp.ua.lifebook.moments.MomentStorage;
import pp.ua.lifebook.plan.PlansStorage;
import pp.ua.lifebook.user.UsersStorage;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "pp.ua.lifebook.storage")
@EntityScan(basePackages = "pp.ua.lifebook.storage")
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
    public PlansStorage plansJdbc(DataSource dataSource) {
        return new PlansDbStorage(dataSource);
    }

    @Bean
    public UsersStorage usersJdbc(DataSource dataSource) {
        return new UsersDbStorage(dataSource);
    }

    @Bean
    public MomentStorage momentStorage(DataSource dataSource) {
        return new MomentDbStorage(dataSource);
    }
}
