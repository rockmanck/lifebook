package pp.ua.lifebook.tag.port;

import pp.ua.lifebook.tag.Tag;

import java.util.List;

public interface TagRepositoryPort {
    List<Tag> search(int userId, String term);
}
