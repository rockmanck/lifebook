package ua.lifebook.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pp.ua.lifebook.DayItemsManager;
import pp.ua.lifebook.ItemsByDay;
import pp.ua.lifebook.user.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/items")
@Controller
public class ItemsController extends BaseController {
    private final DayItemsManager dayItemsManager;

    public ItemsController(DayItemsManager dayItemsManager) {
        this.dayItemsManager = dayItemsManager;
    }

    @RequestMapping("/daily.html")
    public ModelAndView getDailyPlans(@RequestParam LocalDate date, HttpServletRequest request) {
        final User user = user(request);
        final List<ItemsByDay> dailyPlans = dayItemsManager.getForDay(date, user);
        return new ModelAndView("plans/list")
            .addObject("plans", dailyPlans)
            .addObject("viewType", "ViewType.DAILY");
    }

    @RequestMapping("/weekly.html")
    public ModelAndView getWeeklyPlans(@RequestParam LocalDate date, HttpServletRequest request) {
        final User user = user(request);
        final List<ItemsByDay> itemsByDay = dayItemsManager.getForWeek(date, user);
        return new ModelAndView("plans/list")
            .addObject("plans", itemsByDay)
            .addObject("viewType", "ViewType.WEEKLY");
    }
}
