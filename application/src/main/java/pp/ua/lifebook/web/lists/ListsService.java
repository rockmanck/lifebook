package pp.ua.lifebook.web.lists;

import org.springframework.stereotype.Service;
import pp.ua.lifebook.storage.db.list.ListsRepository;
import pp.ua.lifebook.storage.db.scheme.tables.records.ListItemsRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.ListsRecord;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ListsService {
    private final ListsRepository repository;

    public ListsService(ListsRepository repository) {
        this.repository = repository;
    }

    public List<ListsDto> getFor(int userId) {
        return repository.getListsWithItemsFor(userId)
            .entrySet()
            .stream()
            .map(entry -> {
                final List<ListItemsRecord> items = entry.getValue()
                    .stream()
                    .filter(i -> i.getId() != null)
                    .toList();
                return new ListsDto(entry.getKey(), items);
            })
            .toList();
    }

    @Transactional
    public void persist(ListsRecord list, List<ListItemsRecord> items) {
        final int listId = repository.save(list);
        setListIdTo(items, listId);
        repository.save(items);
    }

    private void setListIdTo(List<ListItemsRecord> items, int listId) {
        for (ListItemsRecord item : items) {
            item.setListId(listId);
        }
    }
}
