package ua.lifebook.db;

import ua.lifebook.plans.Plan;
import ua.lifebook.plans.PlanStatus;
import ua.lifebook.users.User;
import ua.lifebook.users.parameters.ViewOption;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PlansService {
    void savePlan(Plan plan);

    Plan getPlan(Integer id);

    /**
     * Returns all plan for specified date and user. Sorts collection by due time.
     */
    List<Plan> getPlans(Date start, Date end, User user, Set<ViewOption> viewOptions);

    void updatePlanStatus(int planId, PlanStatus status);
}
