package ua.lifebook.plans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.lifebook.db.PlansJdbc;
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
    @Autowired private PlansJdbc plansJdbc;

    public void save(Plan plan, User user) {
//        final Integer id = plan.getId();
//        if (id != null) {
//           merge(plan, lifeBookJdbc.getPlan(id));
//        }
        plan.setUser(user);
        plansJdbc.savePlan(plan);
    }

    public List<Plan> getPlans(LocalDate date, User user) {
        return getPlans(date, date, user);
    }

    public List<PlansByDay> getWeekPlans(LocalDate date, User user) {
        final LocalDate end = date.plusWeeks(1);
        final List<Plan> plans = getPlans(date, end, user);
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

    public Plan getPlan(int id) {
        return plansJdbc.getPlan(id);
    }

    private List<Plan> getPlans(LocalDate start, LocalDate end, User user) {
        return plansJdbc.getPlans(DateUtils.localDateToDate(start), DateUtils.localDateToDate(end), user);
    }

    public void donePlan(int id) {
        plansJdbc.update("UPDATE plans SET status = ? WHERE id = ?", PlanStatus.DONE.getCode(), id);
    }

//    private void merge(Plan source, Plan target) {
//        if (target == null) return;
//
//        target.setCategory(source.getCategory());
//        target.setComments(source.getComments());
//        target.setDueDate(source.getDueDate());
//        target.setRepeated(source.isRepeated());
//        target.setStatus(source.getStatus());
//        target.setTitle(source.getTitle());
//    }
}
