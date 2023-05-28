package pp.ua.lifebook.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import pp.ua.lifebook.moments.Moment;
import pp.ua.lifebook.moments.MomentService;
import pp.ua.lifebook.web.config.security.SecurityUtil;

@RequestMapping("/moment")
@Controller
public class MomentController {
    private final MomentService momentService;

    public MomentController(MomentService momentService) {
        this.momentService = momentService;
    }

    @PostMapping("/save.html")
    @ResponseStatus(HttpStatus.OK)
    public void savePlan(@ModelAttribute Moment moment) {
        momentService.save(moment, SecurityUtil.getUser().user());
    }

    @RequestMapping("/{id}/edit.html")
    public ModelAndView editPlan(@PathVariable int id) {
        final Moment moment = momentService.getMoment(id);
        return new ModelAndView("momentForm")
            .addObject("moment", moment);
    }
}
