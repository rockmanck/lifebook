package pp.ua.lifebook.db.list;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import pp.ua.lifebook.list.ListItem;
import pp.ua.lifebook.list.Lists;
import pp.ua.lifebook.list.port.ListsRepositoryPort;
import pp.ua.lifebook.storage.db.scheme.tables.pojos.ListItems;
import pp.ua.lifebook.storage.db.scheme.tables.records.ListItemsRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.ListsRecord;

import javax.sql.DataSource;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pp.ua.lifebook.db.list.ListMapper.toDomainList;
import static pp.ua.lifebook.db.list.ListMapper.toDomainListItems;
import static pp.ua.lifebook.storage.db.scheme.Tables.LISTS;
import static pp.ua.lifebook.storage.db.scheme.Tables.LIST_ITEMS;

@Repository
public class ListsRepository implements ListsRepositoryPort {

    private final DSLContext dslContext;

    public ListsRepository(DataSource dataSource) {
        dslContext = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Override
    public List<Lists> getFor(int userId) {
        return dslContext.select()
            .from(LISTS)
            .where(LISTS.USER_ID.eq(userId).and(LISTS.DELETED.eq(false)))
            .fetch()
            .into(pp.ua.lifebook.storage.db.scheme.tables.pojos.Lists.class)
            .stream()
            .map(ListMapper::toDomainList)
            .sorted(Comparator.comparing(Lists::getName))
            .toList();
    }

    @Override
    public List<ListItem> getItemsFor(int listId) {
        return dslContext.select()
            .from(LIST_ITEMS)
            .where(LIST_ITEMS.LIST_ID.eq(listId))
            .orderBy(LIST_ITEMS.ID)
            .fetch()
            .into(ListItems.class)
            .stream()
            .map(ListMapper::toDomainListItem)
            .toList();
    }

    /**
     * Returns list id
     */
    @Override
    public int save(Lists list) {
        final boolean deleted = list.getDeleted() != null ? list.getDeleted() : false;
        if (list.getId() != null) {
            dslContext.update(LISTS)
                .set(LISTS.USER_ID, list.getUserId())
                .set(LISTS.NAME, list.getName())
                .set(LISTS.DELETED, deleted)
                .where(LISTS.ID.eq(list.getId()))
                .execute();
            return list.getId();
        } else {
            final Record1<Integer> result = dslContext.insertInto(LISTS, LISTS.USER_ID, LISTS.NAME, LISTS.DELETED)
                .values(list.getUserId(), list.getName(), deleted)
                .returningResult(LISTS.ID)
                .fetchOne();
            return result.value1();
        }
    }

    @Override
    public void save(List<ListItem> items) {
        final Map<Boolean, List<ListItemsRecord>> splitByIdExisting = items.stream()
            .map(ListMapper::toJooqRecord)
            .collect(Collectors.partitioningBy(i -> i.getId() != null));

        dslContext.batchInsert(splitByIdExisting.get(false))
            .execute();

        dslContext.batchUpdate(splitByIdExisting.get(true))
            .execute();
    }

    @Override
    public Lists get(int listId) {
        final Record listRecord = dslContext.select()
            .from(LISTS)
            .where(LISTS.ID.eq(listId))
            .fetchOne();
        return listRecord != null ? listRecord.into(Lists.class) : null;
    }

    @Override
    public void deleteList(int listId) {
        dslContext.update(LISTS)
            .set(LISTS.DELETED, true)
            .where(LISTS.ID.eq(listId))
            .execute();
    }

    @Override
    public void deleteListItem(int listId, int itemId) {
        dslContext.delete(LIST_ITEMS)
            .where(LIST_ITEMS.LIST_ID.eq(listId).and(LIST_ITEMS.ID.eq(itemId)))
            .execute();
    }

    @Override
    public Map<Lists, List<ListItem>> getListsWithItemsFor(int userId) {
        return dslContext.select()
            .from(LISTS)
            .leftJoin(LIST_ITEMS)
            .on(LISTS.ID.eq(LIST_ITEMS.LIST_ID))
            .where(LISTS.USER_ID.eq(userId).and(LISTS.DELETED.eq(false)))
            .orderBy(LISTS.NAME)
            .fetchGroups(
                key -> key.into(LISTS).into(ListsRecord.class),
                val -> val.into(LIST_ITEMS).into(ListItemsRecord.class)
            ).entrySet()
            .stream()
            .map(e -> new AbstractMap.SimpleEntry<>(toDomainList(e.getKey()), toDomainListItems(e.getValue())))
            .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }
}
