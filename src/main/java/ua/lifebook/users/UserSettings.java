package ua.lifebook.users;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public final class UserSettings {
    private AtomicReference<Set<ViewOption>> viewOptions = new AtomicReference<>(new HashSet<>());
    private DefaultTab defaultTab;

    public DefaultTab getDefaultTab() {
        return defaultTab;
    }

    public void setDefaultTab(DefaultTab defaultTab) {
        this.defaultTab = defaultTab;
    }

    public void setViewOptions(Set<ViewOption> options) {
        viewOptions.set(options);
    }

    public synchronized Set<ViewOption> getViewOptions() {
        return viewOptions.get();
    }
}
