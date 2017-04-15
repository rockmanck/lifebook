package ua.lifebook.notification;

import java.time.LocalDateTime;

public class Notification {
    private final int userId;
    private final String notificationText;
    private final LocalDateTime notificationTime;

    public Notification(int userId, String notificationText, LocalDateTime notificationTime) {
        this.userId = userId;
        this.notificationText = notificationText;
        this.notificationTime = notificationTime;
    }

    public int getUserId() {
        return userId;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        if (userId != that.userId) return false;
        return notificationText.equals(that.notificationText);
    }

    @Override public int hashCode() {
        int result = userId;
        result = 31 * result + notificationText.hashCode();
        return result;
    }
}
