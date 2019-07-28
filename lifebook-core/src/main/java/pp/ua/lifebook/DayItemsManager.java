package pp.ua.lifebook;

import pp.ua.lifebook.moments.MomentStorage;
import pp.ua.lifebook.plan.Plan;
import pp.ua.lifebook.plan.PlansStorage;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.parameters.ViewOption;
import pp.ua.lifebook.utils.DateUtils;
import pp.ua.lifebook.utils.collections.ListMultimap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DayItemsManager {
    private static final Set<ViewOption> monthlyViewOptions = Set.of(ViewOption.SHOW_DONE, ViewOption.SHOW_CANCELED);

    private final PlansStorage plansStorage;
    private final MomentStorage momentStorage;

    public DayItemsManager(PlansStorage plansStorage, MomentStorage momentStorage) {
        this.plansStorage = plansStorage;
        this.momentStorage = momentStorage;
    }

    public List<ItemsByDay> getForDay(LocalDate date, User user) {
        return get(date, date, user, getUserViewOptions(user));
    }

    public List<ItemsByDay> getForWeek(LocalDate date, User user) {
        final LocalDate end = date.plusWeeks(1);
        return get(date, end, user, getUserViewOptions(user));
    }

    public Map<Integer, ItemsByDay> getMonthlyPlans(int year, int month, User user) {
        final LocalDate start = LocalDate.of(year, month, 1);
        final LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return get(start, end, user, monthlyViewOptions).stream()
            .collect(Collectors.toMap(ItemsByDay::getDayOfMonth, e -> e));
    }

    private List<ItemsByDay> get(
        LocalDate start,
        LocalDate end,
        User user,
        Set<ViewOption> viewOptions
    ) {
        final ListMultimap<LocalDate, Plan> plans = getPlans(start, end, user, viewOptions);

        final Map<LocalDate, ItemsByDay> map = new HashMap<>();

        for (var entry : plans.entrySet()) {
            final LocalDate day = entry.getKey();
            final ItemsByDay itemsByDay = map.computeIfAbsent(day, d -> new ItemsByDay(day));

            for (Plan plan : entry.getValue())
                itemsByDay.addPlan(plan);
        }

        final List<ItemsByDay> result = new ArrayList<>(map.values());
        Collections.sort(result);
        return result;
    }

    private ListMultimap<LocalDate, Plan> getPlans(LocalDate start, LocalDate end, User user, Set<ViewOption> viewOptions) {
        final List<Plan> plans = plansStorage.getPlans(DateUtils.localDateToDate(start), DateUtils.localDateToDate(end), user, viewOptions);
        final ListMultimap<LocalDate, Plan> result = new ListMultimap<>();
        for (Plan p : plans) {
            final LocalDate date = p.getDueDate().toLocalDate();
            result.get(date).add(p);
        }
        return result;
    }

    private Set<ViewOption> getUserViewOptions(User user) {
        return user.getUserSettings().getViewOptions();
    }
}
