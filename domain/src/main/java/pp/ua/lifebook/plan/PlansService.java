package pp.ua.lifebook.plan;

import pp.ua.lifebook.plan.port.PlansStoragePort;
import pp.ua.lifebook.user.User;

public class PlansService {
    private final PlansStoragePort plansStoragePort;

    public PlansService(PlansStoragePort plansStoragePort) {
        this.plansStoragePort = plansStoragePort;
    }

    public void save(Plan plan, User user) {
        plan.setUser(user);
        plansStoragePort.savePlan(plan);
    }

    public Plan getPlan(int id, int userId) {
        return plansStoragePort.getPlan(id, userId);
    }

    public void donePlan(int id) {
        plansStoragePort.updatePlanStatus(id, PlanStatus.DONE);
    }

    public void cancelPlan(int id) {
        plansStoragePort.updatePlanStatus(id, PlanStatus.CANCELED);
    }
}
