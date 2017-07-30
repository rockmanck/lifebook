package ua.lifebook.reminders;

import java.time.LocalDateTime;
import java.util.List;

public interface RemindersService {
    Reminder createReminder(int planId, LocalDateTime remindTime);

    List<Reminder> getReminders(int userId, LocalDateTime from, LocalDateTime to);

    void updateReminder(int reminderId, LocalDateTime remindTime);

    void removeReminder(int reminderId);

    void removePlanReminders(int planId);

    Reminder getReminder(int reminderId);
}
