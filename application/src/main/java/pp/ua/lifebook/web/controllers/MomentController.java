package pp.ua.lifebook.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pp.ua.lifebook.moments.Moment;
import pp.ua.lifebook.moments.MomentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/moment")
@Controller
public class MomentController extends BaseController {
    private final MomentService momentService;

    public MomentController(MomentService momentService) {
        this.momentService = momentService;
    }

    @PostMapping("/save.html")
    public void savePlan(@ModelAttribute Moment moment, HttpServletRequest request, HttpServletResponse response) throws IOException {
        momentService.save(moment, user(request));
        ok(response);
    }

    @RequestMapping("/{id}/edit.html")
    public ModelAndView editPlan(@PathVariable int id) {
        final Moment moment = momentService.getMoment(id);
        return new ModelAndView("momentForm")
            .addObject("moment", moment);
    }
}
