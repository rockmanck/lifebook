package pp.ua.lifebook.tag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import pp.ua.lifebook.tag.port.TagRepositoryPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    private static class RepositorySpy implements TagRepositoryPort {
        private boolean repositoryInvoked = false;

        @Override
        public List<Tag> search(int userId, String term) {
            repositoryInvoked = true;
            return List.of();
        }
    }
}