package pp.ua.lifebook.web.lists;

import org.springframework.stereotype.Service;
import pp.ua.lifebook.storage.db.list.ListsRepository;
import pp.ua.lifebook.storage.db.scheme.tables.pojos.ListItems;

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
                final List<ListItems> items = entry.getValue()
                    .stream()
                    .filter(i -> i.getId() != null)
                    .toList();
                return new ListsDto(entry.getKey(), items);
            })
            .toList();
    }
}
