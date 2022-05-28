package pp.ua.lifebook.testutil;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

public class PostgresTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {

    private PostgreSQLContainer<?> postgres;

    @SuppressWarnings("resource")
    @Override
    public Map<String, String> start() {
        postgres = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("lifebook")
            .withUsername("postgres")
            .withPassword("123");
        postgres.start();
        return Map.of(
            "quarkus.datasource.jdbc.url", "jdbc:postgresql://localhost:" + postgres.getMappedPort(5432) + "/lifebook"
        );
    }

    @Override
    public void stop() {
        postgres.stop();
    }
}
