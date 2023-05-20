package pp.ua.lifebook.web.lists;

import pp.ua.lifebook.list.ListItem;
import pp.ua.lifebook.list.Lists;

import java.util.List;
import java.util.function.Function;

public class ListsDto {
    private Lists list;
    private List<ListItem> listItems;

    public ListsDto(Lists lists, List<ListItem> items) {
        this.list = lists;
        this.listItems = items;
    }

    public Lists getList() {
        return list;
    }

    public List<ListItem> getListItems() {
        return listItems;
    }

    public void setList(Lists list) {
        this.list = list;
    }

    public void setListItems(List<ListItem> listItems) {
        this.listItems = listItems;
    }

    @Override
    public String toString() {
        Function<Object, String> indent = o -> o == null ? null : o.toString().replace("\n", "\n    ");
        return "ListsDto{\n" +
            "     list=" + indent.apply(list) + '\n' +
            "     listItems=" + indent.apply(listItems) + '\n' +
            '}';
    }
}
