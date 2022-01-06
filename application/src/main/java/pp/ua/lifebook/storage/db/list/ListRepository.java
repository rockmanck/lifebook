package pp.ua.lifebook.storage.db.list;

import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ListRepository {

    private final DataSource dataSource;

    public ListRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    void get() {
        DSL.using(dataSource, SQLDialect.POSTGRES);
    }
}
