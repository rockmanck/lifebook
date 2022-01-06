package pp.ua.lifebook.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pp.ua.lifebook.web.user.UserDto;
import pp.ua.lifebook.web.user.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/user/registration")
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registrationPage(UserDto userDto) {
        return "registration";
    }

    @PostMapping
    public String register(@Valid UserDto user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            erasePasswordAndPutToModel(user, model);
            return "registration";
        }
        userService.registerNewUser(user);
        return "redirect:/";
    }

    private void erasePasswordAndPutToModel(UserDto user, Model model) {
        user.setPassword(null);
        user.setMatchingPassword(null);
        model.addAttribute("user", user);
    }
}
