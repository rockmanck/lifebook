package pp.ua.lifebook.tag.port;

import pp.ua.lifebook.tag.Tag;

import java.util.Collection;
import java.util.List;

public interface TagRepositoryPort {
    List<Tag> search(int userId, String term);
    Collection<Tag> create(Collection<Tag> tags, int userId);
    List<Tag> mostPopular(int userId, int limit);
}
