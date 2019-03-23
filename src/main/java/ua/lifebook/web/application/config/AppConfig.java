package ua.lifebook.web.application.config;

import com.typesafe.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ua.lifebook.db.plan.PlansDbStorage;
import ua.lifebook.db.user.UsersDbStorage;
import ua.lifebook.notification.MailManager;
import ua.lifebook.plan.PlansStorage;
import ua.lifebook.reminders.RemindersService;
import ua.lifebook.reminders.RemindersServiceImpl;
import ua.lifebook.user.UsersStorage;

@Configuration
@ComponentScan(
    value = {"ua.lifebook"},
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASPECTJ,
        pattern = {"ua.lifebook.web.*"}
    )
)
@PropertySource(factory = ConfigPropertySourceFactory.class, value = "application.conf")
public class AppConfig {
	public static final Config config = new ConfigurationHolder().getConfig().getConfig("lb");

    @Bean
    public MailManager mailManager(JavaMailSenderImpl sender) {
        return new MailManager(sender, config.getString("transport.mailSender.from"));
    }

    @Bean
    public DbDataSourceHolder dbDataSourceHolder() {
        return new DbDataSourceHolder();
    }

    @Bean
    public PlansStorage plansJdbc(DbDataSourceHolder dbDataSourceHolder) {
        return new PlansDbStorage(dbDataSourceHolder.getDataSource());
    }

    @Bean
    public UsersStorage usersJdbc(DbDataSourceHolder dbDataSourceHolder) {
        return new UsersDbStorage(dbDataSourceHolder.getDataSource());
    }

    @Bean
    public RemindersService remindersService() {
        return new RemindersServiceImpl();
    }

    @Bean
    public JavaMailSenderImpl sender() {
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
