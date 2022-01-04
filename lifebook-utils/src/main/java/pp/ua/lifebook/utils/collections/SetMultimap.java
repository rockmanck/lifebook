package pp.ua.lifebook.utils.collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SetMultimap<K, V> extends HashMap<K, Set<V>> {
    @Override
    @SuppressWarnings("unchecked")
    public Set<V> get(Object key) {
        return computeIfAbsent((K) key, k -> new HashSet<>());
    }

    public void putItem(K key, V value) {
        final Set<V> set = computeIfAbsent(key, k -> new HashSet<>());
        set.add(value);
    }
}
