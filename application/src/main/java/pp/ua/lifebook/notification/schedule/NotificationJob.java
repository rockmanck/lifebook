package pp.ua.lifebook.notification.schedule;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(NotificationJob.class);

    @Override public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.debug("Job started");
        final JobDetail jobDetail = context.getJobDetail();
        final JobDataMap jobDataMap = jobDetail.getJobDataMap();
        logger.debug("PlanId = {}", jobDataMap.getInt("planId"));
    }
}
