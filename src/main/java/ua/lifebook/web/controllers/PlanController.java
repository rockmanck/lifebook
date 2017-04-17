package ua.lifebook.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.lifebook.plans.Plan;
import ua.lifebook.plans.PlanStatus;
import ua.lifebook.plans.PlansByDay;
import ua.lifebook.plans.PlansManager;
import ua.lifebook.reminders.RemindersService;
import ua.lifebook.users.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/plan")
@Controller
public class PlanController extends BaseController {
    @Autowired private PlansManager plansManager;
    @Autowired private RemindersService remindersService;

    @RequestMapping(value = "/save.html", method = RequestMethod.POST)
    public void savePlan(@ModelAttribute Plan plan, HttpServletRequest request, HttpServletResponse response) throws IOException {
        plansManager.save(plan, user(request));
        ok(response);
    }

    @RequestMapping("/daily.html")
    public ModelAndView getDailyPlans(@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate date, HttpServletRequest request) {
        final User user = user(request);
        final List<PlansByDay> dailyPlans = plansManager.getDailyPlans(date, user);
        return new ModelAndView("plans/list")
            .addObject("plans", dailyPlans)
            .addObject("viewType", "ViewType.DAILY");
    }

    @RequestMapping("/weekly.html")
    public ModelAndView getWeeklyPlans(@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate date, HttpServletRequest request) {
        final User user = user(request);
        final List<PlansByDay> plansByDay = plansManager.getWeekPlans(date, user);
        return new ModelAndView("plans/list")
            .addObject("plans", plansByDay)
            .addObject("viewType", "ViewType.WEEKLY");
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