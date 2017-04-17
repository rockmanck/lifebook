package ua.lifebook.notification;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.lifebook.reminders.Reminder;
import ua.lifebook.reminders.RemindersService;
import ua.lifebook.utils.DateUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class NotificationService {
    @Autowired private RemindersService remindersService;

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private Scheduler scheduler;
    private final CopyOnWriteArrayList<NotificationObserver> observers = new CopyOnWriteArrayList<>();
    private final Set<Integer> trackedUsers = ConcurrentHashMap.newKeySet();

    // on new login load reminders which will occur in "near future"
    public void trackNotificationsForUser(int userId) {
        if (trackedUsers.contains(userId)) return;

        trackedUsers.add(userId);
        final List<Reminder> reminders = remindersService.getReminders(userId, LocalDateTime.now(), LocalDateTime.now().plusMonths(1));
        final String groupId = String.valueOf(userId);
        for (Reminder reminder : reminders) {
            scheduleReminder(groupId, reminder);
        }
    }

    /**
     *
     * @param planId
     * @param remindExactTime
     */
    public void addNotification(int planId, LocalDateTime remindExactTime) {

    }

    private void scheduleReminder(String groupId, Reminder reminder) {
        final int planId = reminder.getPlanId();
        final JobDetail job = JobBuilder.newJob(NotificationJob.class)
            .withIdentity("Reminder for " + planId, groupId)
            .usingJobData("planId", planId)
            .build();
        final Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("Trigger for " + reminder.getId(), groupId)
            .startAt(DateUtils.localDateTimeToDate(reminder.getRemindTime()))
            .build();
        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error("Failed to schedule reminder " + reminder, e);
        }
    }

    // add notification subscribers

    // schedule new notifications and when time occurs notify subscribers

    @PostConstruct
    private void init() throws SchedulerException {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
//        final Reminder reminder = new Reminder(1, 1, LocalDateTime.now().plusSeconds(10));
//        scheduleReminder("test", reminder);
    }

    @PreDestroy
    private void shutdown() throws SchedulerException {
        scheduler.shutdown();
    }
}
