package ua.lifebook.plans;

import org.springframework.format.annotation.DateTimeFormat;
import ua.lifebook.admin.User;
import ua.lifebook.db.repository.Identifiable;
import ua.lifebook.db.repository.Table;
import ua.lifebook.utils.DateUtils;

import java.time.LocalDateTime;

@Table("Plans")
public class Plan extends Identifiable {
    private String title;
    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
    private LocalDateTime dueDate;
    private RepeatType repeated;
    private String comments;
    private PlanStatus status;
    private Category category;
    private User user;

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
}
