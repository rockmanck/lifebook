package pp.ua.lifebook.web.moment;

import pp.ua.lifebook.web.tag.TagDto;

import java.time.LocalDate;
import java.util.List;

public class MomentDto {
    private Integer id;
    private LocalDate date;
    private String description;
    private int userId;
    private List<TagDto> tags;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }
}
