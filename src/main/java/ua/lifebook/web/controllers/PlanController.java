package ua.lifebook.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pp.ua.lifebook.plan.ItemsByDay;
import pp.ua.lifebook.plan.Plan;
import pp.ua.lifebook.plan.PlanStatus;
import pp.ua.lifebook.plan.PlansManager;
import pp.ua.lifebook.user.User;
import ua.lifebook.reminders.RemindersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/plan")
@Controller
public class PlanController extends BaseController {
    @Autowired private PlansManager plansManager;
    @Autowired private RemindersService remindersService;

    @InitBinder
    public void customDateFormatBinding(WebDataBinder binder) {
//        final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        simpleDateFormat.setLenient(false);
        binder.registerCustomEditor(LocalDateTime.class, new CustomDateEditor(simpleDateFormat, false));
    }

    @PostMapping("/save.html")
    public void savePlan(@ModelAttribute Plan plan, HttpServletRequest request, HttpServletResponse response) throws IOException {
        plansManager.save(plan, user(request));
        ok(response);
    }

    @RequestMapping("/daily.html")
    public ModelAndView getDailyPlans(@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate date, HttpServletRequest request) {
        final User user = user(request);
        final List<ItemsByDay> dailyPlans = plansManager.getDailyPlans(date, user);
        return new ModelAndView("plans/list")
            .addObject("plans", dailyPlans)
            .addObject("viewType", "ViewType.DAILY");
    }

    @RequestMapping("/weekly.html")
    public ModelAndView getWeeklyPlans(@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate date, HttpServletRequest request) {
        final User user = user(request);
        final List<ItemsByDay> itemsByDay = plansManager.getWeekPlans(date, user);
        return new ModelAndView("plans/list")
            .addObject("plans", itemsByDay)
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
