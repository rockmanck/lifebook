package ua.lifebook.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pp.ua.lifebook.DayItemsManager;
import pp.ua.lifebook.ItemsByDay;
import pp.ua.lifebook.plan.OverviewPlans;
import pp.ua.lifebook.plan.PlanStatus;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.UsersStorage;
import pp.ua.lifebook.user.parameters.DefaultTab;
import pp.ua.lifebook.user.parameters.UserSettings;
import pp.ua.lifebook.user.parameters.ViewOption;
import ua.lifebook.web.utils.SessionKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class MainController extends BaseController {
    private final DayItemsManager dayItemsManager;
    private final UsersStorage usersStorage;

    public MainController(DayItemsManager dayItemsManager, UsersStorage usersStorage) {
        this.dayItemsManager = dayItemsManager;
        this.usersStorage = usersStorage;
    }

    @RequestMapping("/")
    public ModelAndView root(HttpServletRequest request) {
        return index(request);
    }

    @RequestMapping("/index.html")
    public ModelAndView index(HttpServletRequest request) {
        final User user = user(request);
        final UserSettings userSettings = user.getUserSettings();
        final Set<String> userViewOptions = userSettings
            .getViewOptions()
            .stream()
            .map(ViewOption::toString)
            .collect(Collectors.toSet());

        final DefaultTab defaultTab = userSettings.getDefaultTab();
        final List<ItemsByDay> plans;
        final LocalDate now = LocalDate.now();
        final ModelAndView result = new ModelAndView("index");
        switch (defaultTab) {
            case DAILY:
                plans = dayItemsManager.getForDay(now, user);
                break;
            case WEEKLY:
                plans = dayItemsManager.getForWeek(now, user);
                break;
            case OVERVIEW:
                final int year = now.getYear();
                final int month = now.getMonthValue();
                final Map<Integer, ItemsByDay> monthlyPlans = dayItemsManager.getMonthlyPlans(year, month, user);
                result.addObject("plansOverview", new OverviewPlans(year, month, monthlyPlans));
                plans = new ArrayList<>();
                break;
            default:
                plans = new ArrayList<>();
                break;
        }

        return result
            .addObject("planStatuses", PlanStatus.values())
            .addObject("plans", plans)
            .addObject("userViewOptions", userViewOptions)
            .addObject("defaultTab", userSettings.getDefaultTab().name())
            .addObject("user", user);
    }

    @RequestMapping("/updateUserSettings.html")
    public void updateViewOptions(
        @RequestParam String viewOptions,
        @RequestParam String defaultTab,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        usersStorage.updateSettings(viewOptions, defaultTab, user(request));
        ok(response);
    }

    @RequestMapping("/logout.html")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute(SessionKeys.USER);
        response.sendRedirect("/");
    }

    @RequestMapping("/overview.html")
    public ModelAndView overview(@RequestParam int year, @RequestParam int month, HttpServletRequest request) {
        final Map<Integer, ItemsByDay> plans = dayItemsManager.getMonthlyPlans(year, month, user(request));
        return new ModelAndView("overview/overviewContent")
            .addObject("plansOverview", new OverviewPlans(year, month, plans));
    }
}
