package pp.ua.lifebook.quarkusapp.db.util;

import io.agroal.api.AgroalDataSource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ApplicationScoped
public class JdbcTemplate {

    @Inject
    AgroalDataSource dataSource;

    public PreparedStatement statement(String sql) {
        return tryCreateStatement(sql);
    }

    private PreparedStatement tryCreateStatement(String sql) {
        try {
            return dataSource.getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            throw new CreateStatementException(e);
        }
    }

    private static class CreateStatementException extends RuntimeException {
        CreateStatementException(SQLException e) {
            super(e);
        }
    }
}
