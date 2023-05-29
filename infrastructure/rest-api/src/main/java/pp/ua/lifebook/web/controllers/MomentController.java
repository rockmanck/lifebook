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
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.web.config.security.SecurityUtil;
import pp.ua.lifebook.web.moment.MomentDto;
import pp.ua.lifebook.web.moment.MomentDtoMapper;

@RequestMapping("/moment")
@Controller
public class MomentController {
    private final MomentService momentService;

    public MomentController(MomentService momentService) {
        this.momentService = momentService;
    }

    @PostMapping("/save.html")
    @ResponseStatus(HttpStatus.OK)
    public void saveMoment(@ModelAttribute MomentDto dto) {
        final User user = SecurityUtil.getUser().user();
        final Moment moment = MomentDtoMapper.toDomain(dto, user.getId());
        momentService.save(moment, user);
    }

    @RequestMapping("/{id}/edit.html")
    public ModelAndView editPlan(@PathVariable int id) {
        final Integer userId = SecurityUtil.getUser().user().getId();
        final Moment moment = momentService.getMoment(id, userId);
        return new ModelAndView("momentForm")
            .addObject("moment", moment);
    }
}
