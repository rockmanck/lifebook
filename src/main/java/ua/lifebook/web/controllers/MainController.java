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
import ua.lifebook.admin.User;
import ua.lifebook.plans.Plan;
import ua.lifebook.plans.PlanStatus;
import ua.lifebook.plans.PlansManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class MainController extends BaseController {
    @Autowired private PlansManager plansManager;

    @RequestMapping("/")
    public ModelAndView root(HttpServletRequest request) {
        return index(request);
    }

    @RequestMapping("/index.html")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("index")
            .addObject("planStatuses", PlanStatus.values())
            .addObject("todayPlans",  plansManager.getPlans(LocalDate.now(), user(request)))
            .addObject("weekPlans",  plansManager.getWeekPlans(LocalDate.now(), user(request)));
    }

    @RequestMapping(value = "/plan/save.html", method = RequestMethod.POST)
    public void savePlan(@ModelAttribute Plan plan, HttpServletRequest request, HttpServletResponse response) throws IOException {
        plansManager.save(plan, user(request));
        ok(response);
    }

    @RequestMapping("/plan/daily.html")
    public ModelAndView getDailyPlans(@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate date, HttpServletRequest request) {
        final User user = user(request);
        final List<Plan> plans = plansManager.getPlans(date, user);
        return new ModelAndView("plans/dailyList").addObject("plans", plans);
    }

    @RequestMapping("/plan/weekly.html")
    public ModelAndView getWeeklyPlans(@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate date, HttpServletRequest request) {
        final User user = user(request);
        final List<Plan> plans = plansManager.getWeekPlans(date, user);
        return new ModelAndView("plans/weeklyList").addObject("wplans", plans);
    }

    @RequestMapping("/plan/{id}/edit.html")
    public ModelAndView editPlan(@PathVariable int id) {
        final Plan plan = plansManager.getPlan(id);
        return new ModelAndView("planForm")
            .addObject("plan", plan)
            .addObject("planStatuses", PlanStatus.values());
    }
}
