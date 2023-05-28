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
        List<Tag> result = tagRepositoryPort.search(userId, term.toLowerCase());
        return appendNewTagSuggestion(result, userId, term);
    }

    private List<Tag> appendNewTagSuggestion(List<Tag> result, int userId, String term) {
        if (containsExactMatch(result, term)) {
            return result;
        }
        result.add(new Tag(null, userId, term, true, false));
        return result;
    }

    private boolean containsExactMatch(List<Tag> result, String term) {
        return result.stream()
            .anyMatch(t -> t.name().equalsIgnoreCase(term));
    }
}
