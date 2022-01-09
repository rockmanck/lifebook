package pp.ua.lifebook.web.lists;

import pp.ua.lifebook.storage.db.scheme.tables.pojos.ListItems;
import pp.ua.lifebook.storage.db.scheme.tables.pojos.Lists;

import java.util.List;

public class ListsDto {
    private final Lists list;
    private final List<ListItems> items;

    public ListsDto(Lists lists, List<ListItems> items) {
        this.list = lists;
        this.items = items;
    }

    public Lists getList() {
        return list;
    }

    public List<ListItems> getItems() {
        return items;
    }
}
