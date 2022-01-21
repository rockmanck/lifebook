package pp.ua.lifebook.web.lists;

import pp.ua.lifebook.storage.db.scheme.tables.records.ListItemsRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.ListsRecord;

import java.util.List;

public class ListsDto {
    private ListsRecord list;
    private List<ListItemsRecord> listItems;

    public ListsDto(ListsRecord lists, List<ListItemsRecord> items) {
        this.list = lists;
        this.listItems = items;
    }

    public ListsRecord getList() {
        return list;
    }

    public List<ListItemsRecord> getListItems() {
        return listItems;
    }

    public void setList(ListsRecord list) {
        this.list = list;
    }

    public void setListItems(List<ListItemsRecord> listItems) {
        this.listItems = listItems;
    }
}
