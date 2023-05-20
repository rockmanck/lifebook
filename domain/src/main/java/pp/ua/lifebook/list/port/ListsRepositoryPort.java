package pp.ua.lifebook.list.port;

import pp.ua.lifebook.list.ListItem;
import pp.ua.lifebook.list.Lists;

import java.util.List;
import java.util.Map;

public interface ListsRepositoryPort {
    List<Lists> getFor(int userId);

    List<ListItem> getItemsFor(int listId);

    int save(Lists list);

    void save(List<ListItem> items);

    Lists get(int listId);

    void deleteList(int listId);

    void deleteListItem(int listId, int itemId);

    Map<Lists, List<ListItem>> getListsWithItemsFor(int userId);
}
