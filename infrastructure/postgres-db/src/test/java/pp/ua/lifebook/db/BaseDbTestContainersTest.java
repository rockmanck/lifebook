package pp.ua.lifebook.db;

import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pp.ua.lifebook.db.user.UsersDbStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
public class BaseDbTestContainersTest {
    @Container
    protected static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    protected static int userId = 1;
    protected static Connection connection;
    protected static DSLContext dslContext;
    protected static UsersDbStorage usersDbStorage;


    @BeforeAll
    static void init() throws SQLException {
        // Create a connection to the database
        connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword());

        // Create DSL context
        dslContext = DSL.using(connection, SQLDialect.POSTGRES);
        dslContext.execute("CREATE ROLE postgres LOGIN");

        Flyway.configure()
                .dataSource(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())
                .locations("classpath:/db/migration")
                .outOfOrder(true)
                .load()
                .migrate();
        usersDbStorage = new UsersDbStorage(dslContext.dsl().parsingDataSource(), dslContext);
    }

    @AfterAll
    static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
