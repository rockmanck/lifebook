package pp.ua.lifebook;

import pp.ua.lifebook.moments.Moment;
import pp.ua.lifebook.moments.MomentStorage;
import pp.ua.lifebook.plan.Plan;
import pp.ua.lifebook.plan.PlansStorage;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.parameters.ViewOption;
import pp.ua.lifebook.utils.collections.ListMultimap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
        final List<ItemsByDay> itemsByDays = get(date, end, user, getUserViewOptions(user));
        Collections.sort(itemsByDays);
        return itemsByDays;
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
        final ListMultimap<LocalDate, Moment> moments = getMoments(start, end, user);
        final Set<LocalDate> allDates = mergeDates(plans.keySet(), moments.keySet());

        final Map<LocalDate, ItemsByDay> map = new HashMap<>();

        for (var date : allDates) {
            final ItemsByDay itemsByDay = map.computeIfAbsent(date, ItemsByDay::new);

            for (Plan plan : plans.get(date))
                itemsByDay.addPlan(plan);

            for (Moment moment : moments.get(date))
                itemsByDay.addMoment(moment);
        }

        return new ArrayList<>(map.values());
    }

    private Set<LocalDate> mergeDates(Set<LocalDate> dates1, Set<LocalDate> dates2) {
        final Set<LocalDate> result = new HashSet<>(dates1);
        result.addAll(dates2);
        return result;
    }

    private ListMultimap<LocalDate, Plan> getPlans(LocalDate start, LocalDate end, User user, Set<ViewOption> viewOptions) {
        final List<Plan> plans = plansStorage.getPlans(start, end, user, viewOptions);
        final ListMultimap<LocalDate, Plan> result = new ListMultimap<>();
        for (Plan p : plans) {
            final LocalDate date = p.getDueDate().toLocalDate();
            result.get(date).add(p);
        }
        return result;
    }

    private ListMultimap<LocalDate, Moment> getMoments(LocalDate start, LocalDate end, User user) {
        final List<Moment> moments = momentStorage.getByDateRange(user.getId(), start, end);
        final ListMultimap<LocalDate, Moment> result = new ListMultimap<>();
        for (Moment m : moments) {
            final LocalDate date = m.getDate();
            result.get(date).add(m);
        }
        return result;
    }

    private Set<ViewOption> getUserViewOptions(User user) {
        return user.getUserSettings().getViewOptions();
    }
}
