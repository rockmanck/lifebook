package ua.lifebook.config;

import com.typesafe.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ua.lifebook.db.PlansService;
import ua.lifebook.db.PlansServiceImpl;
import ua.lifebook.db.UsersJdbc;
import ua.lifebook.db.repository.ReplicationManager;
import ua.lifebook.notification.MailManager;
import ua.lifebook.reminders.RemindersService;
import ua.lifebook.reminders.RemindersServiceImpl;

@Configuration
@ComponentScan("ua.lifebook")
public class AppConfig {
	public static final Config config = new ConfigurationHolder().getConfig().getConfig("lb");

    @Bean public MailManager mailManager() {
        return new MailManager(sender(), config.getString("transport.mailSender.from"));
    }

    @Bean public PlansService plansJdbc() {
        return new PlansServiceImpl(DbConfig.dataSource());
    }

    @Bean public UsersJdbc usersJdbc() {
        return new UsersJdbc(DbConfig.dataSource());
    }

    @Bean public ReplicationManager replicationManager() {
        final Config replication = config.getConfig("replication");
        return new ReplicationManager(replication.getBoolean("isPrimary"), replication.getString("storage"));
    }

    @Bean public RemindersService remindersService() {
        return new RemindersServiceImpl();
    }

    private JavaMailSenderImpl sender() {
        final JavaMailSenderImpl sender = new JavaMailSenderImpl();
        final Config sendConf = config.getConfig("transport.mailSender");
        sender.setHost(sendConf.getString("host"));
        sender.setPort(sendConf.getInt("port"));
        sender.setUsername(sendConf.getString("username"));
        sender.setPassword(sendConf.getString("password"));
        sender.setDefaultEncoding(sendConf.getString("defaultEncoding"));
        return sender;
    }
}
