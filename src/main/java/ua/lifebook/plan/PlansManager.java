package ua.lifebook.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.lifebook.user.User;
import ua.lifebook.user.parameters.ViewOption;
import ua.lifebook.utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PlansManager {
    private final PlansStorage plansStorage;
    private static final Set<ViewOption> allViewOptions = new HashSet<>(Arrays.asList(ViewOption.values()));

    @Autowired
    public PlansManager(PlansStorage plansStorage) {
        this.plansStorage = plansStorage;
    }

    public void save(Plan plan, User user) {
        plan.setUser(user);
        plansStorage.savePlan(plan);
    }

    public List<PlansByDay> getDailyPlans(LocalDate date, User user) {
        return getPlans(date, date, user, getUserViewOptions(user));
    }

    public List<PlansByDay> getWeekPlans(LocalDate date, User user) {
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

    public Map<Integer, PlansByDay> getMonthlyPlans(int year, int month, User user) {
        final LocalDate start = LocalDate.of(year, month, 1);
        final LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return getPlans(start, end, user, allViewOptions).stream()
            .collect(Collectors.toMap(PlansByDay::getDayOfMonth, e -> e));
    }

    private List<PlansByDay> getPlans(LocalDate start,
                                      LocalDate end,
                                      User user,
                                      Set<ViewOption> viewOptions) {
        final List<Plan> plans = plansStorage.getPlans(DateUtils.localDateToDate(start), DateUtils.localDateToDate(end), user, viewOptions);
        final Map<LocalDate, PlansByDay> map = new HashMap<>();

        for (Plan plan : plans) {
            final LocalDate day = plan.getDueDate().toLocalDate();
            if (!map.containsKey(day)) {
                map.put(day, new PlansByDay(plan.getDueDate()));
            }
            map.get(day).addPlan(plan);
        }

        final List<PlansByDay> result = new ArrayList<>(map.values());
        Collections.sort(result);
        return result;
    }

    private Set<ViewOption> getUserViewOptions(User user) {
        return user.getUserSettings().getViewOptions();
    }
}