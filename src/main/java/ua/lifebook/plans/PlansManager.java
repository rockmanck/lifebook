package ua.lifebook.plans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.lifebook.admin.User;
import ua.lifebook.db.PlansJdbc;
import ua.lifebook.utils.DateUtils;

import java.time.LocalDate;
import java.util.List;

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

    public List<Plan> getWeekPlans(LocalDate date, User user) {
        final LocalDate end = date.plusWeeks(1);
        return getPlans(date, end, user);
    }

    public Plan getPlan(int id) {
        return plansJdbc.getPlan(id);
    }

    private List<Plan> getPlans(LocalDate start, LocalDate end, User user) {
        return plansJdbc.getPlans(DateUtils.localDateToDate(start), DateUtils.localDateToDate(end), user);
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
