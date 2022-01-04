package pp.ua.lifebook.notification;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pp.ua.lifebook.notification.schedule.ScheduleUtils;
import pp.ua.lifebook.reminders.Reminder;
import pp.ua.lifebook.reminders.RemindersService;
import pp.ua.lifebook.utils.collections.SetMultimap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotificationService {
    @Autowired private RemindersService remindersService;

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private Scheduler scheduler;
    private final SetMultimap<Integer, NotificationObserver> observers = new SetMultimap<>();

    /**
     * Subscribes for notifications for user with id {@code userId}
     * @param observer object that will receive notifications
     */
    public void subscribe(NotificationObserver observer, int userId) {
        if (!observers.containsKey(userId)) {
            final List<Reminder> remindersForUser = getRemindersForUser(userId);
            remindersForUser.forEach(this::schedule);
        }
        observers.putItem(userId, observer);
    }

    public void unsubscribe(NotificationObserver observer, int userId) {
        observers.remove(userId, observer);
        if (!observers.containsKey(userId)) {
            final List<Reminder> remindersForUser = getRemindersForUser(userId);
            remindersForUser.forEach(this::unschedule);
        }
    }

    public void updateNotification(Reminder reminder) {
        try {
            final TriggerKey triggerKey = ScheduleUtils.getTriggerKey(reminder);
            scheduler.rescheduleJob(triggerKey, ScheduleUtils.getTrigger(reminder));
        } catch (SchedulerException e) {
            logger.error("Failed to update notification " + reminder, e);
        }
    }

    public void removeNotification(Reminder reminder) {
        unschedule(reminder);
    }

//    @Scheduled
    private void reloadRemindersForTrackedUsers() {
        // TODO add once per day schedule, load reminders for a week and replace/add them in scheduler
    }

    private List<Reminder> getRemindersForUser(int userId) {
        final LocalDateTime startDate = LocalDateTime.now();
        final LocalDateTime endDate = startDate.plusWeeks(1);
        return remindersService.getReminders(userId, startDate, endDate);
    }

    private void schedule(Reminder reminder) {
        final JobDetail job = ScheduleUtils.getJob(reminder);
        final Trigger trigger = ScheduleUtils.getTrigger(reminder);
        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error("Failed to schedule reminder " + reminder, e);
        }
    }

    private void unschedule(Reminder reminder) {
        try {
            scheduler.unscheduleJob(ScheduleUtils.getTriggerKey(reminder));
        } catch (SchedulerException e) {
            logger.error("Failed to unschedule " + reminder, e);
        }
    }

    @PostConstruct
    private void init() throws SchedulerException {
//        scheduler = StdSchedulerFactory.getDefaultScheduler();
//        scheduler.start();
//        final Reminder reminder = new Reminder(1, 1, LocalDateTime.now().plusSeconds(10));
//        scheduleReminder("test", reminder);
    }

    @PreDestroy
    private void shutdown() throws SchedulerException {
        scheduler.shutdown();
    }
}
