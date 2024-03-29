package pp.ua.lifebook.reminders;

import java.time.LocalDateTime;

public class Reminder {
    private final int id;
    private final int planId;
    private final int userId;
    private final LocalDateTime remindTime;

    public Reminder(int id, int planId, int userId, LocalDateTime remindTime) {
        this.id = id;
        this.planId = planId;
        this.userId = userId;
        this.remindTime = remindTime;
    }

    public int getId() {
        return id;
    }

    public int getPlanId() {
        return planId;
    }

    public LocalDateTime getRemindTime() {
        return remindTime;
    }

    public int getUserId() {
        return userId;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reminder reminder = (Reminder) o;

        return id == reminder.id;
    }

    @Override public int hashCode() {
        return id;
    }

    @Override public String toString() {
        return "Reminder{" +
            "id=" + id +
            ", planId=" + planId +
            ", userId=" + userId +
            ", remindTime=" + remindTime +
            '}';
    }
}
