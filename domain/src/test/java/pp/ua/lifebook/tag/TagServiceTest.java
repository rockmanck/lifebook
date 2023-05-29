package pp.ua.lifebook.tag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import pp.ua.lifebook.tag.port.TagRepositoryPort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pp.ua.lifebook.tag.TagBuilder.aNewTag;
import static pp.ua.lifebook.tag.TagBuilder.aTag;

class TagServiceTest {

    private final int userId = 1;
    private final RepositorySpy spy = new RepositorySpy();
    private final TagService sut = new TagService(spy);

    @DisplayName("When term is null or empty Then return empty list")
    @ParameterizedTest(name = "#{index} - term = [{0}]")
    @NullSource
    @ValueSource(strings = {"", " "})
    void emptyTerm(String term) {
        List<Tag> result = sut.search(userId, term);
        assertThat(result).isEmpty();
        assertThat(spy.repositoryInvoked).isFalse();
    }

    @Test
    @DisplayName("When no exact match from repository Then add new tag suggestion")
    void newTagSuggestion() {
        Tag exampleTag = aTag(userId, "example");
        List<Tag> stub = new ArrayList<>();
        stub.add(exampleTag);
        spy.stub = stub;

        List<Tag> result = sut.search(userId, "ex");

        assertThat(result).isEqualTo(List.of(exampleTag, aNewTag(userId, "ex")));
    }

    @Test
    @DisplayName("When exact match exists Then do not add new tag suggestion")
    void noNewTagSuggestion() {
        Tag tag = aTag(userId, "match");
        spy.stub = List.of(tag);

        List<Tag> result = sut.search(userId, "match");

        assertThat(result).isEqualTo(List.of(tag));

    }

    private static class RepositorySpy implements TagRepositoryPort {
        private boolean repositoryInvoked = false;
        private List<Tag> stub = List.of();

        @Override
        public List<Tag> search(int userId, String term) {
            repositoryInvoked = true;
            return stub;
        }

        @Override
        public Collection<Tag> create(Collection<Tag> tags, int userId) {
            return null;
        }
    }
}