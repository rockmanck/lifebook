package pp.ua.lifebook.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pp.ua.lifebook.plan.Plan;
import pp.ua.lifebook.plan.PlanStatus;
import pp.ua.lifebook.plan.PlansService;
import pp.ua.lifebook.web.config.security.SecurityUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/plan")
@Controller
public class PlanController extends BaseController {
    private final PlansService plansService;

    public PlanController(PlansService plansService) {
        this.plansService = plansService;
    }

    @PostMapping("/save.html")
    public void savePlan(@ModelAttribute Plan plan, HttpServletResponse response) throws IOException {
        plansService.save(plan, SecurityUtil.getUser().user());
        ok(response);
    }

    @RequestMapping("/{id}/edit.html")
    public ModelAndView editPlan(@PathVariable int id) {
        final Plan plan = plansService.getPlan(id);
        return new ModelAndView("plans/planForm")
            .addObject("plan", plan)
            .addObject("planStatuses", PlanStatus.values());
    }

    @RequestMapping("/{id}/done.html")
    public void done(@PathVariable int id, HttpServletResponse response) throws IOException {
        plansService.donePlan(id);
        ok(response);
    }

    @RequestMapping("/{id}/cancel.html")
    public void cancel(@PathVariable int id, HttpServletResponse response) throws IOException {
        plansService.cancelPlan(id);
        ok(response);
    }

    @RequestMapping("/{id}/addRemind.html")
    public void addRemind(@PathVariable int planId, HttpServletResponse response) throws IOException {
        ok(response);
    }
}
