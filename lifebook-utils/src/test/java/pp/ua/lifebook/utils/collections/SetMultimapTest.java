package pp.ua.lifebook.utils.collections;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SetMultimapTest {
    @Test
    void whenGetOnMissingKey_thenReturnEmptySet() {
        SetMultimap<String, Integer> mset = new SetMultimap<>();
        assertThat(mset.get("unknown")).isEqualTo(new HashSet<>());
    }

    @Test
    void whenPutFirstTime_thenNewSetCreated() {
        final String key = "key";
        final int val = 1;

        SetMultimap<String, Integer> mset = new SetMultimap<>();
        mset.putItem(key, val);

        assertThat(mset.get(key)).isEqualTo(Set.of(val));
    }
}