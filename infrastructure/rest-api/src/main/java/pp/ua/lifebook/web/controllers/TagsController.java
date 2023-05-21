package pp.ua.lifebook.web.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pp.ua.lifebook.tag.TagService;
import pp.ua.lifebook.web.tag.TagDto;
import pp.ua.lifebook.web.tag.TagDtoMapper;
import pp.ua.lifebook.web.user.LbUserPrincipal;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {

    private final TagService tagService;

    public TagsController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/suggest")
    public List<TagDto> suggest(@RequestParam String term, @AuthenticationPrincipal LbUserPrincipal principal) {
        return tagService.search(principal.user().getId(), term)
            .stream()
            .map(TagDtoMapper::toDto)
            .toList();
    }
}
