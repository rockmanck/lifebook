package pp.ua.lifebook.reminders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pp.ua.lifebook.notification.NotificationService;
import pp.ua.lifebook.plan.Plan;
import pp.ua.lifebook.plan.PlansService;

import java.time.LocalDateTime;

@Component
public class RemindersManager {
    @Autowired private RemindersService remindersService;
    @Autowired private PlansService plansService;
    @Autowired private NotificationService notificationService;

    public void addReminder(int planId, RemindBefore remindBefore, int userId) {
        final LocalDateTime remindTime = getRemindBeforeTime(planId, remindBefore, userId);
        addReminder(planId, remindTime);
    }

    public void addReminder(int planId, LocalDateTime remindExactTime) {
        final Reminder reminder = remindersService.createReminder(planId, remindExactTime);
        notificationService.updateNotification(reminder);
    }

    public void updateReminder(int reminderId, RemindBefore remindBefore, int userId) {
        final Reminder reminder = remindersService.getReminder(reminderId);
        final LocalDateTime remindExactTime = getRemindBeforeTime(reminder.getPlanId(), remindBefore, userId);
        updateReminder(reminderId, remindExactTime);
        notificationService.updateNotification(remindersService.getReminder(reminderId));
    }

    public void updateReminder(int reminderId, LocalDateTime remindExactTime) {
        remindersService.updateReminder(reminderId, remindExactTime);
        notificationService.updateNotification(remindersService.getReminder(reminderId));
    }

    public void removeRemindersForPlan(int planId) {
        remindersService.removePlanReminders(planId);
    }

    public void removeReminder(int reminderId) {
        remindersService.removeReminder(reminderId);
    }

    private LocalDateTime getRemindBeforeTime(int planId, RemindBefore remindBefore, int userId) {
        final Plan plan = plansService.getPlan(planId, userId);
        return remindBefore.getRemindTime(plan.getDueDate());
    }
}
