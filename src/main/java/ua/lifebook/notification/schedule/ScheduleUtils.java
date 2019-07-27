package ua.lifebook.notification.schedule;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import pp.ua.lifebook.utils.DateUtils;
import ua.lifebook.reminders.Reminder;

public final class ScheduleUtils {

    private ScheduleUtils() {
        // hide public constructor
    }

    public static JobDetail getJob(Reminder reminder) {
        final int planId = reminder.getPlanId();
        final String groupId = groupId(reminder.getUserId());
        return JobBuilder.newJob(NotificationJob.class)
            .withIdentity(jobId(planId), groupId)
            .usingJobData(JobDataKey.PLAN_ID.name(), planId)
            .build();
    }

    public static Trigger getTrigger(Reminder reminder) {
        final String groupId = groupId(reminder.getUserId());
        return TriggerBuilder.newTrigger()
            .withIdentity(triggerId(reminder), groupId)
            .startAt(DateUtils.localDateTimeToDate(reminder.getRemindTime()))
            .build();
    }

    public static String jobId(int planId) {
        return "reminder_for_" + planId;
    }

    public static String triggerId(Reminder reminder) {
        return "trigger_for_" + reminder.getId();
    }

    public static String groupId(int userId) {
        return "group_for_user_" + userId;
    }

    public static TriggerKey getTriggerKey(Reminder reminder) {
        final String name = triggerId(reminder);
        final String group = groupId(reminder.getUserId());
        return new TriggerKey(name, group);
    }
}
