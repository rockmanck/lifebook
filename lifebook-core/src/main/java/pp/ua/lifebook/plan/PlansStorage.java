package pp.ua.lifebook.plan;

import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.parameters.ViewOption;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface PlansStorage {
    void savePlan(Plan plan);

    Plan getPlan(Integer id);

    /**
     * Returns all plan for specified date and user. Sorts collection by due time.
     */
    List<Plan> getPlans(LocalDate start, LocalDate end, User user, Set<ViewOption> viewOptions);

    void updatePlanStatus(int planId, PlanStatus status);
}
