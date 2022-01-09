package pp.ua.lifebook.storage.db.list;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import pp.ua.lifebook.storage.db.scheme.tables.pojos.Lists;
import pp.ua.lifebook.storage.db.scheme.tables.records.ListsRecord;

import javax.sql.DataSource;
import java.util.List;

import static pp.ua.lifebook.storage.db.scheme.Tables.LISTS;

@Repository
public class ListsRepository {

    private final DSLContext dslContext;

    public ListsRepository(DataSource dataSource) {
        dslContext = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    public List<Lists> getFor(int userId) {
        return dslContext.select()
            .from(LISTS)
            .where(LISTS.USER_ID.eq(userId).and(LISTS.DELETED.eq(false)))
            .fetch()
            .into(Lists.class);
    }

    public void save(ListsRecord list) {
        final boolean deleted = list.getDeleted() != null ? list.getDeleted() : false;
        dslContext.insertInto(LISTS, LISTS.USER_ID, LISTS.NAME, LISTS.DELETED)
            .values(list.getUserId(), list.getName(), deleted)
            .onDuplicateKeyUpdate()
            .set(LISTS.NAME, list.getName())
            .set(LISTS.DELETED, deleted)
            .execute();
    }

    public Lists get(int listId) {
        final Record listRecord = dslContext.select()
            .from(LISTS)
            .where(LISTS.ID.eq(listId))
            .fetchOne();
        return listRecord != null ? listRecord.into(Lists.class) : null;
    }

    public void deleteList(int listId) {
        dslContext.update(LISTS)
            .set(LISTS.DELETED, true)
            .where(LISTS.ID.eq(listId))
            .execute();
    }
}
