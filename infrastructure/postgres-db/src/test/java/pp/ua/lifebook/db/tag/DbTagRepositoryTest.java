package pp.ua.lifebook.db.tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pp.ua.lifebook.db.BaseDbTestContainersTest;
import pp.ua.lifebook.db.plan.PlansDbStorage;
import pp.ua.lifebook.plan.Plan;
import pp.ua.lifebook.plan.PlanStatus;
import pp.ua.lifebook.tag.Tag;
import pp.ua.lifebook.user.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class DbTagRepositoryTest extends BaseDbTestContainersTest {

    private DbTagRepository tagRepository;
    private PlansDbStorage planRepository;

    @BeforeEach
    void setUp() {
        // Clean up any existing data
        dslContext.execute("TRUNCATE TABLE tag RESTART IDENTITY");

        // Create repository
        tagRepository = new DbTagRepository(dslContext);
        planRepository = new PlansDbStorage(dslContext.dsl().parsingDataSource(), dslContext, tagRepository);
    }

    @Test
    void shouldCreateTag() {
        // Given
        Tag tag = new Tag(null, userId, "Test Tag");

        // When
        Collection<Tag> createdTags = tagRepository.create(Set.of(tag), userId);

        // Then
        assertThat(createdTags).hasSize(1);
        Tag createdTag = createdTags.iterator().next();
        assertThat(createdTag.id()).isNotNull();
        assertThat(createdTag.userId()).isEqualTo(userId);
        assertThat(createdTag.name()).isEqualTo("Test Tag");
    }

    @Test
    void shouldSearchTagsByName() {
        // Given
        Tag tag1 = new Tag(null, userId, "Java");
        Tag tag2 = new Tag(null, userId, "JavaScript");
        Tag tag3 = new Tag(null, userId, "Python");
        tagRepository.create(Set.of(tag1, tag2, tag3), userId);

        // When
        List<Tag> foundTags = tagRepository.search(userId, "java");

        // Then
        assertThat(foundTags).hasSize(2);
        assertThat(foundTags.stream().map(Tag::name))
                .containsExactly("Java", "JavaScript");
    }

    @Test
    void shouldReturnEmptyListWhenNoTagsMatch() {
        // Given
        Tag tag1 = new Tag(null, userId, "Java");
        Tag tag2 = new Tag(null, userId, "JavaScript");
        tagRepository.create(Set.of(tag1, tag2), userId);

        // When
        List<Tag> foundTags = tagRepository.search(userId, "python");

        // Then
        assertThat(foundTags).isEmpty();
    }

    @Test
    void shouldNotCreateTagIfItHasIdAndIsNotRemoved() {
        // Given
        Tag tag = new Tag(123, userId, "Existing Tag");

        // When
        Collection<Tag> result = tagRepository.create(Set.of(tag), userId);

        // Then
        assertThat(result).hasSize(1);
        Tag resultTag = result.iterator().next();
        assertThat(resultTag.id()).isEqualTo(123);
        assertThat(resultTag.name()).isEqualTo("Existing Tag");
    }

    @Test
    @DisplayName("""
            When asked for most popular tags Then returns tags sorted by usage count, same count in alphabetical order
            """)
    void shouldReturnMostPopularTags() {
        // Given
        final Tag usedTwiceTag = createTag("B Used two times");
        final Tag usedTwiceTag2 = createTag("A Used two times");
        final Tag usedOnceTag = createTag("Used one time");
        final Tag notUsedTag = createTag("not used");
        createPlan("plan1", List.of(usedTwiceTag, usedOnceTag, usedTwiceTag2));
        createPlan("plan2", List.of(usedTwiceTag, usedTwiceTag2));
        int limit = 5;

        // When
        List<Tag> mostPopularTags = tagRepository.mostPopular(userId, limit);

        // Then
        assertThat(mostPopularTags)
                .isNotEmpty()
                .isEqualTo(List.of(usedTwiceTag2, usedTwiceTag, usedOnceTag, notUsedTag));
    }

    private Tag createTag(String name) {
        final Collection<Tag> tags = tagRepository.create(Set.of(new Tag(null, userId, name)), userId);
        return tags.iterator().next();
    }

    private void createPlan(String planTitle, List<Tag> tags) {
        final Plan plan = Plan.builder()
                .setTitle(planTitle)
                .setTags(tags)
                .setStatus(PlanStatus.SCHEDULED)
                .setUser(User.builder().setId(userId).createUser())
                .createPlan();
        planRepository.savePlan(plan);
    }
}
