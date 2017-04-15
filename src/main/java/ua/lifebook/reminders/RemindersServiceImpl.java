package ua.lifebook.reminders;

import java.time.LocalDateTime;
import java.util.List;

public class RemindersServiceImpl implements RemindersService {
    @Override public void createReminder(int planId, LocalDateTime remindTime) {

    }

    @Override public List<Reminder> getReminders(int userId, LocalDateTime from, LocalDateTime to) {
        return null;
    }

    @Override public void updateReminder(int reminderId, LocalDateTime remindTime) {

    }

    @Override public void removeReminder(int reminderId) {

    }

    @Override public void removePlanReminders(int planId) {

    }

    @Override public Reminder getReminder(int reminderId) {
        return null;
    }
}
