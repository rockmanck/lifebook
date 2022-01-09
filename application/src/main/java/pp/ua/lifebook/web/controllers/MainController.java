package pp.ua.lifebook.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pp.ua.lifebook.DayItemsManager;
import pp.ua.lifebook.ItemsByDay;
import pp.ua.lifebook.plan.OverviewPlans;
import pp.ua.lifebook.plan.PlanStatus;
import pp.ua.lifebook.storage.db.list.ListsRepository;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.UsersStorage;
import pp.ua.lifebook.user.parameters.DefaultTab;
import pp.ua.lifebook.user.parameters.UserSettings;
import pp.ua.lifebook.user.parameters.ViewOption;
import pp.ua.lifebook.web.config.security.SecurityUtil;
import pp.ua.lifebook.web.utils.SessionKeys;

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
    private final ListsRepository listsRepository;

    public MainController(DayItemsManager dayItemsManager, UsersStorage usersStorage, ListsRepository listsRepository) {
        this.dayItemsManager = dayItemsManager;
        this.usersStorage = usersStorage;
        this.listsRepository = listsRepository;
    }

    @RequestMapping("/")
    public ModelAndView root() {
        return index();
    }

    @RequestMapping("/index.html")
    public ModelAndView index() {
        final User user = SecurityUtil.getUser().user();
        final UserSettings userSettings = user.getUserSettings();
        final Set<String> userViewOptions = userSettings
            .getViewOptions()
            .stream()
            .map(ViewOption::toString)
            .collect(Collectors.toSet());

        final DefaultTab defaultTab = userSettings.getDefaultTab();
        List<ItemsByDay> items = new ArrayList<>();
        final LocalDate now = LocalDate.now();
        final ModelAndView result = new ModelAndView("index");
        switch (defaultTab) {
            case DAILY -> items = dayItemsManager.getForDay(now, user);
            case WEEKLY -> items = dayItemsManager.getForWeek(now, user);
            case OVERVIEW -> {
                final int year = now.getYear();
                final int month = now.getMonthValue();
                final Map<Integer, ItemsByDay> monthlyPlans = dayItemsManager.getMonthlyPlans(year, month, user);
                result.addObject("plansOverview", new OverviewPlans(year, month, monthlyPlans));
            }
            case LISTS -> result.addObject("lists", listsRepository.getFor(user.getId()));
        }

        return result
            .addObject("planStatuses", PlanStatus.values())
            .addObject("items", items)
            .addObject("userViewOptions", userViewOptions)
            .addObject("defaultTab", userSettings.getDefaultTab().name())
            .addObject("user", user);
    }

    @RequestMapping("/updateUserSettings.html")
    public void updateViewOptions(
        @RequestParam String viewOptions,
        @RequestParam String defaultTab,
        HttpServletResponse response
    ) throws IOException {
        usersStorage.updateSettings(viewOptions, defaultTab, SecurityUtil.getUser().user());
        ok(response);
    }

    @RequestMapping("/logout.html")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute(SessionKeys.USER);
        response.sendRedirect("/");
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/overview.html")
    public ModelAndView overview(@RequestParam int year, @RequestParam int month) {
        final Map<Integer, ItemsByDay> plans = dayItemsManager.getMonthlyPlans(year, month, SecurityUtil.getUser().user());
        return new ModelAndView("overview/overviewContent")
            .addObject("plansOverview", new OverviewPlans(year, month, plans));
    }
}
