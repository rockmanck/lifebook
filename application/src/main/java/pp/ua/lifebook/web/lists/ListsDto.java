package pp.ua.lifebook.web.lists;

import pp.ua.lifebook.storage.db.scheme.tables.records.ListItemsRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.ListsRecord;

import java.util.List;

public class ListsDto {
    private ListsRecord list;
    private List<ListItemsRecord> items;

    public ListsDto(ListsRecord lists, List<ListItemsRecord> items) {
        this.list = lists;
        this.items = items;
    }

    public ListsRecord getList() {
        return list;
    }

    public List<ListItemsRecord> getItems() {
        return items;
    }

    public void setList(ListsRecord list) {
        this.list = list;
    }

    public void setItems(List<ListItemsRecord> items) {
        this.items = items;
    }
}
