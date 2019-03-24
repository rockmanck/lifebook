package ua.lifebook.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.lifebook.plan.OverviewPlans;
import ua.lifebook.plan.PlanStatus;
import ua.lifebook.plan.PlansByDay;
import ua.lifebook.plan.PlansManager;
import ua.lifebook.user.User;
import ua.lifebook.user.UsersStorage;
import ua.lifebook.user.parameters.DefaultTab;
import ua.lifebook.user.parameters.UserSettings;
import ua.lifebook.user.parameters.ViewOption;
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
    private final PlansManager plansManager;
    private final UsersStorage usersStorage;

    @Autowired
    public MainController(PlansManager plansManager, UsersStorage usersStorage) {
        this.plansManager = plansManager;
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
        final List<PlansByDay> plans;
        final LocalDate now = LocalDate.now();
        final ModelAndView result = new ModelAndView("index");
        switch (defaultTab) {
            case DAILY:
                plans = plansManager.getDailyPlans(now, user);
                break;
            case WEEKLY:
                plans = plansManager.getWeekPlans(now, user);
                break;
            case OVERVIEW:
                final int year = now.getYear();
                final int month = now.getMonthValue();
                final Map<Integer, PlansByDay> monthlyPlans = plansManager.getMonthlyPlans(year, month, user);
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
        final Map<Integer, PlansByDay> plans = plansManager.getMonthlyPlans(year, month, user(request));
        return new ModelAndView("overview/overviewContent")
            .addObject("plansOverview", new OverviewPlans(year, month, plans));
    }
}
