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
import pp.ua.lifebook.web.config.security.SecurityUtil;
import pp.ua.lifebook.web.lists.ListsDto;
import pp.ua.lifebook.web.lists.ListsService;

@Controller
@RequestMapping("/lists")
public class ListsController {

    private final ListsRepository listsRepository;
    private final ListsService listsService;

    public ListsController(ListsRepository listsRepository, ListsService listsService) {
        this.listsRepository = listsRepository;
        this.listsService = listsService;
    }

    @PostMapping
    @ResponseBody
    public void save(ListsDto list) {
        Integer userId = SecurityUtil.getUser().user().getId();
        list.getList().setUserId(userId);
        listsService.persist(list.getList(), list.getListItems());
    }

    @GetMapping("/{id}")
    public ModelAndView getEditForm(@PathVariable int id) {
        return new ModelAndView("lists/listForm")
            .addObject("list", listsRepository.get(id))
            .addObject("listItems", listsRepository.getItemsFor(id));
    }

    @GetMapping
    public ModelAndView getAll() {
        final int userId = SecurityUtil.getUser().user().getId();
        return new ModelAndView("lists/lists")
            .addObject("data", listsService.getFor(userId));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteList(@PathVariable int id) {
        listsRepository.deleteList(id);
    }
}
