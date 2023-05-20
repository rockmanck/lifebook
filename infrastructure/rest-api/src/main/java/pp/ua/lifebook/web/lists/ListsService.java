package pp.ua.lifebook.web.lists;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pp.ua.lifebook.list.ListItem;
import pp.ua.lifebook.list.Lists;
import pp.ua.lifebook.list.port.ListsRepositoryPort;

import java.util.Comparator;
import java.util.List;

@Service
public class ListsService {
    private final ListsRepositoryPort repository;

    public ListsService(ListsRepositoryPort repository) {
        this.repository = repository;
    }

    public List<ListsDto> getFor(int userId) {
        return repository.getListsWithItemsFor(userId)
            .entrySet()
            .stream()
            .map(entry -> {
                final List<ListItem> items = entry.getValue()
                    .stream()
                    .filter(i -> i.getId() != null)
                    .sorted(Comparator.comparingInt(ListItem::getId))
                    .toList();
                return new ListsDto(entry.getKey(), items);
            })
            .sorted(Comparator.comparing(o -> o.getList().getName()))
            .toList();
    }

    public void persist(Lists list, List<ListItem> items) {
        final int listId = repository.save(list);
        setListIdTo(items, listId);
        if (!CollectionUtils.isEmpty(items)) {
            repository.save(items);
        }
    }

    private void setListIdTo(List<ListItem> items, int listId) {
        if (CollectionUtils.isEmpty(items)) return;

        for (ListItem item : items) {
            item.setListId(listId);
        }
    }
}
