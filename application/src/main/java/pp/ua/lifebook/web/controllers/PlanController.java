package pp.ua.lifebook.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pp.ua.lifebook.plan.Plan;
import pp.ua.lifebook.plan.PlanStatus;
import pp.ua.lifebook.plan.PlansManager;
import pp.ua.lifebook.reminders.RemindersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/plan")
@Controller
public class PlanController extends BaseController {
    private final PlansManager plansManager;
    private final RemindersService remindersService;

    public PlanController(PlansManager plansManager, RemindersService remindersService) {
        this.plansManager = plansManager;
        this.remindersService = remindersService;
    }

    @PostMapping("/save.html")
    public void savePlan(@ModelAttribute Plan plan, HttpServletRequest request, HttpServletResponse response) throws IOException {
        plansManager.save(plan, user(request));
        ok(response);
    }

    @RequestMapping("/{id}/edit.html")
    public ModelAndView editPlan(@PathVariable int id) {
        final Plan plan = plansManager.getPlan(id);
        return new ModelAndView("planForm")
            .addObject("plan", plan)
            .addObject("planStatuses", PlanStatus.values());
    }

    @RequestMapping("/{id}/done.html")
    public void done(@PathVariable int id, HttpServletResponse response) throws IOException {
        plansManager.donePlan(id);
        ok(response);
    }

    @RequestMapping("/{id}/cancel.html")
    public void cancel(@PathVariable int id, HttpServletResponse response) throws IOException {
        plansManager.cancelPlan(id);
        ok(response);
    }

    @RequestMapping("/{id}/addRemind.html")
    public void addRemind(@PathVariable int planId, HttpServletResponse response) throws IOException {
        ok(response);
    }
}
