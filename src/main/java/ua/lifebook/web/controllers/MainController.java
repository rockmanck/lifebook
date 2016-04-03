package ua.lifebook.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.lifebook.plans.Plan;
import ua.lifebook.plans.PlanStatus;
import ua.lifebook.plans.PlansManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

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
            .addObject("todayPlans",  plansManager.getPlans(LocalDate.now(), user(request)));
    }

    @RequestMapping(value = "/plan/save.html", method = RequestMethod.POST)
    public void savePlan(@ModelAttribute Plan plan, HttpServletRequest request, HttpServletResponse response) throws IOException {
        plansManager.save(plan, user(request));
        ok(response);
    }
}
