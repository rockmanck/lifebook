package pp.ua.lifebook.plan;

import pp.ua.lifebook.user.User;
import pp.ua.lifebook.utils.DateUtils;

import java.time.LocalDateTime;

public class Plan {
    private Integer id;
    private String title;
    private LocalDateTime dueDate;
    private RepeatType repeated;
    private String comments;
    private PlanStatus status;
    private Category category;
    private User user;
    private boolean isOutdated = false;

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
}
