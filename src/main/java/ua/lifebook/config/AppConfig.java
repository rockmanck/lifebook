package ua.lifebook.config;

import com.typesafe.config.Config;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ua.lifebook.jdbc.LifeBookJdbc;
import ua.lifebook.notification.MailManager;

@Configuration
@ComponentScan("ua.lifebook")
public class AppConfig {
	private final Config config = new ConfigurationHolder().getConfig();

    @Bean public MailManager mailManager() {
        return new MailManager(sender(), config.getString("lb.transport.mailSender.from"));
    }

    @Bean public LifeBookJdbc lifeBookJdbc() {
        final LifeBookJdbc jdbc = new LifeBookJdbc();
        final Config db = this.config.getConfig("lb.db");
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(db.getString("username"));
        jdbc.setDataSource(dataSource);
        return jdbc;
    }

    private JavaMailSenderImpl sender() {
        final JavaMailSenderImpl sender = new JavaMailSenderImpl();
        final Config sendConf = config.getConfig("lb.transport.mailSender");
        sender.setHost(sendConf.getString("host"));
        sender.setPort(sendConf.getInt("port"));
        sender.setUsername(sendConf.getString("username"));
        sender.setPassword(sendConf.getString("password"));
        sender.setDefaultEncoding(sendConf.getString("defaultEncoding"));
        return sender;
    }
}
