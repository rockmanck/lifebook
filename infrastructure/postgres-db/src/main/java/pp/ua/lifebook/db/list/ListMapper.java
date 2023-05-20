package pp.ua.lifebook.db.list;

import pp.ua.lifebook.list.ListItem;
import pp.ua.lifebook.list.Lists;
import pp.ua.lifebook.storage.db.scheme.tables.pojos.ListItems;
import pp.ua.lifebook.storage.db.scheme.tables.records.ListItemsRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.ListsRecord;

import java.util.List;

class ListMapper {
    static Lists toDomainList(pp.ua.lifebook.storage.db.scheme.tables.pojos.Lists list) {
        return new Lists()
            .setId(list.getId())
            .setUserId(list.getUserId())
            .setName(list.getName())
            .setDeleted(list.getDeleted());
    }

    static Lists toDomainList(ListsRecord list) {
        return new Lists()
            .setId(list.getId())
            .setUserId(list.getUserId())
            .setName(list.getName())
            .setDeleted(list.getDeleted());
    }

    static List<ListItem> toDomainListItems(List<ListItemsRecord> list) {
        return list.stream()
            .map(ListMapper::toDomainListItem)
            .toList();
    }

    static ListItem toDomainListItem(ListItemsRecord item) {
        return new ListItem()
            .setId(item.getId())
            .setListId(item.getListId())
            .setComment(item.getComment())
            .setName(item.getName())
            .setCompleted(item.getCompleted());
    }

    public static ListItem toDomainListItem(ListItems item) {
        return new ListItem()
            .setId(item.getId())
            .setListId(item.getListId())
            .setComment(item.getComment())
            .setName(item.getName())
            .setCompleted(item.getCompleted());
    }

    public static ListItemsRecord toJooqRecord(ListItem listItem) {
        ListItemsRecord result = new ListItemsRecord()
            .setListId(listItem.getListId())
            .setName(listItem.getName())
            .setComment(listItem.getComment())
            .setCompleted(listItem.getCompleted());
        if (listItem.getId() != null) {
            result.setId(listItem.getId());
        }
        return result;
    }
}
