package pp.ua.lifebook.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import pp.ua.lifebook.plan.Plan;
import pp.ua.lifebook.plan.PlanStatus;
import pp.ua.lifebook.plan.PlansService;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.web.config.security.SecurityUtil;
import pp.ua.lifebook.web.plan.PlanDto;
import pp.ua.lifebook.web.plan.PlanDtoMapper;

@RequestMapping("/plan")
@Controller
public class PlanController {
    private final PlansService plansService;

    public PlanController(PlansService plansService) {
        this.plansService = plansService;
    }

    @PostMapping("/save.html")
    @ResponseStatus(HttpStatus.OK)
    public void savePlan(@ModelAttribute PlanDto plan) {
        User user = SecurityUtil.getUser().user();
        plansService.save(PlanDtoMapper.toDomain(plan, user.getId()), user);
    }

    @RequestMapping("/{id}/edit.html")
    public ModelAndView editPlan(@PathVariable int id) {
        User user = SecurityUtil.getUser().user();
        final Plan plan = plansService.getPlan(id, user.getId());
        return new ModelAndView("plans/planForm")
            .addObject("plan", plan)
            .addObject("planStatuses", PlanStatus.values());
    }

    @RequestMapping("/{id}/done.html")
    @ResponseStatus(HttpStatus.OK)
    public void done(@PathVariable int id) {
        plansService.donePlan(id);
    }

    @RequestMapping("/{id}/cancel.html")
    @ResponseStatus(HttpStatus.OK)
    public void cancel(@PathVariable int id) {
        plansService.cancelPlan(id);
    }

    @RequestMapping("/{id}/addRemind.html")
    @ResponseStatus(HttpStatus.OK)
    public void addRemind(@PathVariable int planId) {
    }
}
