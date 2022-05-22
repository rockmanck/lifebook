package pp.ua.lifebook.quarkusapp.db;

import pp.ua.lifebook.quarkusapp.db.util.JdbcTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class UsersRepository {

    @Inject
    JdbcTemplate jdbc;


    public List<String> getAll() {
        jdbc.statement("select 1");
        return null;
    }
}
