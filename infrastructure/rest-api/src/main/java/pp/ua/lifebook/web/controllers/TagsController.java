package pp.ua.lifebook.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {
    @GetMapping("/suggest")
    public List<Res> suggest(@RequestParam String term) {
        return List.of(
            new Res("tag1", "Tag #1"),
            new Res("tag2", "Tag #2"),
            new Res("tag2", "Tag #2"),
            new Res("tag2", "Tag #2"),
            new Res("tag2", "Tag #2"),
            new Res("tag2", "Tag #2"),
            new Res("tag2", "Tag #2"),
            new Res("tag2", "Tag #2"),
            new Res("tag2", "Tag #2"),
            new Res("tag2", "Tag #2"),
            new Res("tag2", "Tag #2"),
            new Res("tag2", "Tag #2"),
            new Res("tag2", "Tag #2"),
            new Res("tag2", "Tag #2"),
            new Res("tag2", "Tag #2")
        );
    }

    public final class Res {
        private String value;
        private String label;
        private String custom = "custom";

        public Res() {
        }

        public Res(String value, String text) {
            this.value = value;
            this.label = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getCustom() {
            return custom;
        }

        public void setCustom(String custom) {
            this.custom = custom;
        }
    }
}
