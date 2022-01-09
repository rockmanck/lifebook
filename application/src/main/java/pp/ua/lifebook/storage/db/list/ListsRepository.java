package pp.ua.lifebook.storage.db.list;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import pp.ua.lifebook.storage.db.scheme.tables.pojos.ListItems;
import pp.ua.lifebook.storage.db.scheme.tables.pojos.Lists;
import pp.ua.lifebook.storage.db.scheme.tables.records.ListsRecord;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

import static pp.ua.lifebook.storage.db.scheme.Tables.LISTS;
import static pp.ua.lifebook.storage.db.scheme.Tables.LIST_ITEMS;

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

    public List<ListItems> getItemsFor(int listId) {
        return dslContext.select()
            .from(LIST_ITEMS)
            .where(LIST_ITEMS.LIST_ID.eq(listId))
            .fetch()
            .into(ListItems.class);
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

    public Map<Lists, List<ListItems>> getListsWithItemsFor(int userId) {
        return dslContext.select()
            .from(LISTS)
            .leftJoin(LIST_ITEMS)
            .on(LISTS.ID.eq(LIST_ITEMS.LIST_ID))
            .where(LISTS.USER_ID.eq(userId).and(LISTS.DELETED.eq(false)))
            .fetchGroups(
                key -> key.into(LISTS).into(Lists.class),
                val -> val.into(LIST_ITEMS).into(ListItems.class)
            );
    }
}
