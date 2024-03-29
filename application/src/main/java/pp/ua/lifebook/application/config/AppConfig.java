package pp.ua.lifebook.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import pp.ua.lifebook.DayItemsManager;
import pp.ua.lifebook.moments.MomentService;
import pp.ua.lifebook.moments.MomentStorage;
import pp.ua.lifebook.notification.MailManager;
import pp.ua.lifebook.plan.PlansService;
import pp.ua.lifebook.plan.port.PlansStoragePort;
import pp.ua.lifebook.reminders.RemindersService;
import pp.ua.lifebook.reminders.RemindersServiceImpl;
import pp.ua.lifebook.tag.TagService;
import pp.ua.lifebook.tag.port.TagRepositoryPort;

@Configuration
@ComponentScan(
    value = {"pp.ua.lifebook"},
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASPECTJ,
        pattern = {"ua.lifebook.web.*"}
    )
)
@DependsOn("flyway")
public class AppConfig {

    @Bean
    public MailManager mailManager(
        JavaMailSenderImpl sender,
        @Value("${lb.transport.mailSender.from}") String from
    ) {
        return new MailManager(sender, from);
    }

    @Bean
    public RemindersService remindersService() {
        return new RemindersServiceImpl();
    }

    @Bean
    public PlansService plansManager(PlansStoragePort storage) {
        return new PlansService(storage);
    }

    @Bean
    public MomentService momentService(MomentStorage storage) {
        return new MomentService(storage);
    }

    @Bean
    public DayItemsManager dayItemsManager(PlansStoragePort plansStoragePort, MomentStorage momentStorage) {
        return new DayItemsManager(plansStoragePort, momentStorage);
    }

    @Bean
    public JavaMailSenderImpl sender(
        @Value("${lb.transport.mailSender.host}") String host,
        @Value("${lb.transport.mailSender.port}") int port,
        @Value("${lb.transport.mailSender.username}") String username,
        @Value("${lb.transport.mailSender.password}") String password,
        @Value("${lb.transport.mailSender.defaultEncoding}") String encoding
    ) {
        final JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);
        sender.setDefaultEncoding(encoding);
        return sender;
    }

    @Bean
    TagService tagService(TagRepositoryPort tagRepositoryPort) {
        return new TagService(tagRepositoryPort);
    }
}
