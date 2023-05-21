package pp.ua.lifebook.featuretests.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

public class EmbeddedPostgresDb {

    PostgreSQLContainer<?> postgres;

    public EmbeddedPostgresDb() {
        postgres = new PostgreSQLContainer<>("postgres:11")
            .withUsername("postgres");
        postgres.start();
    }

    public DataSource getDataSource() {
        return DataSourceBuilder.create()
            .driverClassName(postgres.getDriverClassName())
            .url(postgres.getJdbcUrl())
            .username(postgres.getUsername())
            .password(postgres.getPassword())
            .build();
    }

    @PreDestroy
    private void close() {
        postgres.close();
    }
}
