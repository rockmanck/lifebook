package pp.ua.lifebook.tag;

import org.apache.commons.lang3.StringUtils;
import pp.ua.lifebook.tag.port.TagRepositoryPort;

import java.util.List;

public class TagService {
    private final TagRepositoryPort tagRepositoryPort;

    public TagService(TagRepositoryPort tagRepositoryPort) {
        this.tagRepositoryPort = tagRepositoryPort;
    }

    public List<Tag> search(int userId, String term) {
        if (StringUtils.isEmpty(term) || StringUtils.isEmpty(term.trim())) {
            return List.of();
        }
        return tagRepositoryPort.search(userId, term.toLowerCase());
    }
}
