package pp.ua.lifebook.plan;

import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.parameters.ViewOption;
import pp.ua.lifebook.utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PlansManager {
    private final PlansStorage plansStorage;
    private static final Set<ViewOption> monthlyViewOptions = Set.of(ViewOption.SHOW_DONE, ViewOption.SHOW_CANCELED);

    public PlansManager(PlansStorage plansStorage) {
        this.plansStorage = plansStorage;
    }

    public void save(Plan plan, User user) {
        plan.setUser(user);
        plansStorage.savePlan(plan);
    }

    public List<ItemsByDay> getDailyPlans(LocalDate date, User user) {
        return getPlans(date, date, user, getUserViewOptions(user));
    }

    public List<ItemsByDay> getWeekPlans(LocalDate date, User user) {
        final LocalDate end = date.plusWeeks(1);
        return getPlans(date, end, user, getUserViewOptions(user));
    }

    public Plan getPlan(int id) {
        return plansStorage.getPlan(id);
    }

    public void donePlan(int id) {
        plansStorage.updatePlanStatus(id, PlanStatus.DONE);
    }

    public void cancelPlan(int id) {
        plansStorage.updatePlanStatus(id, PlanStatus.CANCELED);
    }

    public Map<Integer, ItemsByDay> getMonthlyPlans(int year, int month, User user) {
        final LocalDate start = LocalDate.of(year, month, 1);
        final LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return getPlans(start, end, user, monthlyViewOptions).stream()
            .collect(Collectors.toMap(ItemsByDay::getDayOfMonth, e -> e));
    }

    private List<ItemsByDay> getPlans(
        LocalDate start,
        LocalDate end,
        User user,
        Set<ViewOption> viewOptions
    ) {
        final List<Plan> plans = plansStorage.getPlans(DateUtils.localDateToDate(start), DateUtils.localDateToDate(end), user, viewOptions);
        final Map<LocalDate, ItemsByDay> map = new HashMap<>();

        for (Plan plan : plans) {
            final LocalDate day = plan.getDueDate().toLocalDate();
            if (!map.containsKey(day)) {
                map.put(day, new ItemsByDay(plan.getDueDate()));
            }
            map.get(day).addPlan(plan);
        }

        final List<ItemsByDay> result = new ArrayList<>(map.values());
        Collections.sort(result);
        return result;
    }

    private Set<ViewOption> getUserViewOptions(User user) {
        return user.getUserSettings().getViewOptions();
    }
}
