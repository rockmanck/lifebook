package pp.ua.lifebook.web.plan;

import pp.ua.lifebook.plan.Category;
import pp.ua.lifebook.plan.PlanStatus;
import pp.ua.lifebook.plan.RepeatType;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.utils.DateUtils;
import pp.ua.lifebook.web.tag.TagDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public class PlanDto {
    private Integer id;
    private String title;
    private LocalDateTime dueDate;
    private RepeatType repeated;
    private String comments;
    private PlanStatus status;
    private Category category;
    private User user;
    private boolean isOutdated;
    private List<TagDto> tags;

    public PlanDto() {}

    private PlanDto(
        Integer id,
        String title,
        LocalDateTime dueDate,
        RepeatType repeated,
        String comments,
        PlanStatus status,
        Category category,
        User user,
        boolean isOutdated
    ) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.repeated = repeated;
        this.comments = comments;
        this.status = status;
        this.category = category;
        this.user = user;
        this.isOutdated = isOutdated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getRawDueDate() {
        return dueDate != null ? DateUtils.format(dueDate) : "";
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public RepeatType getRepeated() {
        return repeated;
    }

    public void setRepeated(RepeatType repeated) {
        this.repeated = repeated;
    }

    public String getComments() {
        return comments;
    }
    
    public List<String> getCommentLines() {
        return Stream.of(comments.split("\n"))
            .toList();
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public PlanStatus getStatus() {
        return status;
    }

    public void setStatus(PlanStatus status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isOutdated() {
        return isOutdated;
    }

    public void setOutdated(boolean outdated) {
        isOutdated = outdated;
    }

    public static PlanBuilder builder() {
        return new PlanBuilder();
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

    public static final class PlanBuilder {

        private Integer id;
        private String title;
        private LocalDateTime dueDate;
        private RepeatType repeated;
        private String comments;
        private PlanStatus status;
        private Category category;
        private User user;
        private boolean isOutdated = false;

        private PlanBuilder() {
            // hide public constructor
        }

        public PlanBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public PlanBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public PlanBuilder setDueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public PlanBuilder setRepeated(RepeatType repeated) {
            this.repeated = repeated;
            return this;
        }

        public PlanBuilder setComments(String comments) {
            this.comments = comments;
            return this;
        }

        public PlanBuilder setStatus(PlanStatus status) {
            this.status = status;
            return this;
        }

        public PlanBuilder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public PlanBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public PlanBuilder setIsOutdated(boolean isOutdated) {
            this.isOutdated = isOutdated;
            return this;
        }

        public PlanDto createPlan() {
            return new PlanDto(id, title, dueDate, repeated, comments, status, category, user, isOutdated);
        }
    }
}
