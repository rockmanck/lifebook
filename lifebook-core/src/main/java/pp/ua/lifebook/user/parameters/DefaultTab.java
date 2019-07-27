package pp.ua.lifebook.user.parameters;

import java.util.HashMap;
import java.util.Map;

public enum DefaultTab {
    DAILY,
    WEEKLY,
    SEARCH,
    OVERVIEW;

    private static final Map<String, DefaultTab> byName = new HashMap<>();
    static {
        for (DefaultTab tab : DefaultTab.values())
            byName.put(tab.name(), tab);
    }

    public static DefaultTab byName(String name) {
        return byName.getOrDefault(name, DAILY);
    }
}
