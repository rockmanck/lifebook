package ua.lifebook.reminders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.lifebook.notification.NotificationService;
import ua.lifebook.plans.Plan;
import ua.lifebook.plans.PlansManager;

import java.time.LocalDateTime;

@Component
public class RemindersManager {
    @Autowired private RemindersService remindersService;
    @Autowired private PlansManager plansManager;
    @Autowired private NotificationService notificationService;

    public void addReminder(int planId, RemindBefore remindBefore) {
        final LocalDateTime remindTime = getRemindBeforeTime(planId, remindBefore);
        addReminder(planId, remindTime);
    }

    public void addReminder(int planId, LocalDateTime remindExactTime) {
        final Reminder reminder = remindersService.createReminder(planId, remindExactTime);
        notificationService.updateNotification(reminder);
    }

    public void updateReminder(int reminderId, RemindBefore remindBefore) {
        final Reminder reminder = remindersService.getReminder(reminderId);
        final LocalDateTime remindExactTime = getRemindBeforeTime(reminder.getPlanId(), remindBefore);
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

    private LocalDateTime getRemindBeforeTime(int planId, RemindBefore remindBefore) {
        final Plan plan = plansManager.getPlan(planId);
        return remindBefore.getRemindTime(plan.getDueDate());
    }
}
