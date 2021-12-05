package ua.lifebook.web.application.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.ua.lifebook.db.moment.MomentDbStorage;
import pp.ua.lifebook.db.plan.PlansDbStorage;
import pp.ua.lifebook.db.user.UsersDbStorage;
import pp.ua.lifebook.moments.MomentStorage;
import pp.ua.lifebook.plan.PlansStorage;
import pp.ua.lifebook.user.UsersStorage;
import ua.lifebook.web.application.flyway.FlywayAction;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Bean
    public DataSource dataSource(
        @Value("${lb.db.username}") String username,
        @Value("${lb.db.password}") String password,
        @Value("${lb.db.url}") String url,
        @Value("${lb.db.driver}") String driver
    ) {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    public Flyway flyway(@Value("${lb.db.flyway}") String action, DataSource dataSource) {
        final Flyway flyway = Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/lifebook")
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
