package pp.ua.lifebook.storage.db.list;

import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import pp.ua.lifebook.storage.db.scheme.tables.pojos.Lists;

import javax.sql.DataSource;
import java.util.List;

import static pp.ua.lifebook.storage.db.scheme.Tables.LISTS;

@Repository
public class ListsRepository {

    private final DataSource dataSource;

    public ListsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Lists> getFor(int userId) {
        return DSL.using(dataSource, SQLDialect.POSTGRES)
            .select().from(LISTS)
            .where(LISTS.USER_ID.eq(userId))
            .fetch()
            .into(Lists.class);
    }

    public void save(Lists list) {
        DSL.using(dataSource, SQLDialect.POSTGRES)
            .insertInto(LISTS, LISTS.USER_ID, LISTS.NAME, LISTS.DELETED)
            .values(list.getUserId(), list.getName(), list.getDeleted())
            .onDuplicateKeyUpdate()
            .set(LISTS.NAME, list.getName())
            .set(LISTS.DELETED, list.getDeleted())
            .execute();
    }
}
