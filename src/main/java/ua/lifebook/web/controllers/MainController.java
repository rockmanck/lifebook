package ua.lifebook.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.lifebook.plans.PlanStatus;
import ua.lifebook.plans.PlansByDay;
import ua.lifebook.plans.PlansManager;
import ua.lifebook.users.DefaultTab;
import ua.lifebook.users.User;
import ua.lifebook.users.UserSettings;
import ua.lifebook.users.UsersManager;
import ua.lifebook.users.ViewOption;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class MainController extends BaseController {
    @Autowired private PlansManager plansManager;
    @Autowired private UsersManager usersManager;

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
        switch (defaultTab) {
            case DAILY:
                plans = plansManager.getDailyPlans(now, user);
                break;
            case WEEKLY:
                plans = plansManager.getWeekPlans(now, user);
                break;
            default: plans = new ArrayList<>();
        }

        return new ModelAndView("index")
            .addObject("planStatuses", PlanStatus.values())
            .addObject("plans", plans)
            .addObject("userViewOptions", userViewOptions)
            .addObject("defaultTab", userSettings.getDefaultTab().name());
    }

    @RequestMapping("/updateUserSettings.html")
    public void updateViewOptions(@RequestParam String viewOptions,
                                  @RequestParam String defaultTab,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {
        usersManager.updateSettings(viewOptions, defaultTab, user(request));
        ok(response);
    }
}
