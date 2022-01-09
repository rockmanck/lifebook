package pp.ua.lifebook.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pp.ua.lifebook.storage.db.list.ListsRepository;
import pp.ua.lifebook.storage.db.scheme.tables.pojos.Lists;
import pp.ua.lifebook.storage.db.scheme.tables.records.ListsRecord;
import pp.ua.lifebook.web.config.security.SecurityUtil;

@Controller
@RequestMapping("/lists")
public class ListsController {

    private final ListsRepository listsRepository;

    public ListsController(ListsRepository listsRepository) {
        this.listsRepository = listsRepository;
    }

    @PostMapping
    @ResponseBody
    public void save(ListsRecord list) {
        Integer userId = SecurityUtil.getUser().user().getId();
        list.setUserId(userId);
        listsRepository.save(list);
    }

    @GetMapping("/{id}")
    public ModelAndView getEditForm(@PathVariable int id) {
        final Lists list = listsRepository.get(id);
        return new ModelAndView("lists/listForm")
            .addObject("list", list);
    }

    @GetMapping
    public ModelAndView getAll() {
        final int userId = SecurityUtil.getUser().user().getId();
        return new ModelAndView("lists/lists")
            .addObject("lists", listsRepository.getFor(userId));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteList(@PathVariable int id) {
        listsRepository.deleteList(id);
    }
}
