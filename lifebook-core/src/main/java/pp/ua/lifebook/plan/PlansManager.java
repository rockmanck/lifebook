package pp.ua.lifebook.plan;

import pp.ua.lifebook.user.User;

public class PlansManager {
    private final PlansStorage plansStorage;

    public PlansManager(PlansStorage plansStorage) {
        this.plansStorage = plansStorage;
    }

    public void save(Plan plan, User user) {
        plan.setUser(user);
        plansStorage.savePlan(plan);
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
}
