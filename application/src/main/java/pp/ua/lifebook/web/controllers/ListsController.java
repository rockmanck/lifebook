package pp.ua.lifebook.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pp.ua.lifebook.storage.db.list.ListsRepository;
import pp.ua.lifebook.storage.db.scheme.tables.pojos.Lists;
import pp.ua.lifebook.web.config.security.SecurityUtil;

@Controller
@RequestMapping("/lists")
public class ListsController {

    private final ListsRepository listsRepository;

    public ListsController(ListsRepository listsRepository) {
        this.listsRepository = listsRepository;
    }

    @GetMapping
    public void add() {
        Integer userId = SecurityUtil.getUser().user().getId();
        Lists list = new Lists(null, userId, "test list", false);
        listsRepository.save(list);
    }
}
