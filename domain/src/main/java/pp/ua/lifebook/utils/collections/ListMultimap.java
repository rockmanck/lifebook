package pp.ua.lifebook.utils.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListMultimap<K, V> extends HashMap<K, List<V>> {
    @Override
    @SuppressWarnings("unchecked")
    public List<V> get(Object key) {
        return computeIfAbsent((K) key, k -> new ArrayList<>());
    }
}
