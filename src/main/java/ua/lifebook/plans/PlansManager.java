package ua.lifebook.plans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.lifebook.db.PlansService;
import ua.lifebook.users.User;
import ua.lifebook.utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PlansManager {
    @Autowired private PlansService plansService;

    public void save(Plan plan, User user) {
        plan.setUser(user);
        plansService.savePlan(plan);
    }

    public List<PlansByDay> getDailyPlans(LocalDate date, User user) {
        return getPlans(date, date, user);
    }

    public List<PlansByDay> getWeekPlans(LocalDate date, User user) {
        final LocalDate end = date.plusWeeks(1);
        return getPlans(date, end, user);
    }

    public Plan getPlan(int id) {
        return plansService.getPlan(id);
    }

    public void donePlan(int id) {
        plansService.updatePlanStatus(id, PlanStatus.DONE);
    }

    public void cancelPlan(int id) {
        plansService.updatePlanStatus(id, PlanStatus.CANCELED);
    }

    private List<PlansByDay> getPlans(LocalDate start, LocalDate end, User user) {
        final List<Plan> plans = plansService.getPlans(DateUtils.localDateToDate(start), DateUtils.localDateToDate(end), user);
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
}
