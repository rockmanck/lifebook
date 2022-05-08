package pp.ua.lifebook.user.parameters;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ViewOption {
    SHOW_DONE,
    SHOW_OUTDATED,
    SHOW_CANCELED;

    public static Set<ViewOption> parse(String options) {
        if (options.isEmpty()) return new HashSet<>();

        return Stream.of(options.split(","))
            .map(ViewOption::valueOf)
            .collect(Collectors.toSet());
    }
}
