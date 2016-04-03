package ua.lifebook.config;

import com.typesafe.config.Config;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ua.lifebook.db.PlansJdbc;
import ua.lifebook.db.UsersJdbc;
import ua.lifebook.db.repository.ReplicationManager;
import ua.lifebook.notification.MailManager;
import ua.lifebook.web.Authorization;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ua.lifebook")
public class AppConfig {
	public static final Config config = new ConfigurationHolder().getConfig().getConfig("lb");

    @Bean public MailManager mailManager() {
        return new MailManager(sender(), config.getString("transport.mailSender.from"));
    }

    @Bean public PlansJdbc plansJdbc() {
        return new PlansJdbc(getDataSource());
    }

    @Bean public UsersJdbc usersJdbc() {
        return new UsersJdbc(getDataSource());
    }

    @Bean public ReplicationManager replicationManager() {
        final Config replication = config.getConfig("replication");
        return new ReplicationManager(replication.getBoolean("isPrimary"), replication.getString("storage"));
    }

    @Bean public Authorization authorization() {
        return new Authorization(usersJdbc());
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

    private DataSource getDataSource() {
        final Config db = config.getConfig("db");
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(db.getString("username"));
        dataSource.setPassword(db.getString("password"));
        dataSource.setUrl(db.getString("url"));
        dataSource.setDriverClassName(db.getString("driver"));
        return dataSource;
    }
}
