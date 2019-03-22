package ua.lifebook.web.application.config;

import com.typesafe.config.Config;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public final class DbConfig {
    public static DataSource dataSource() {
        final Config db = AppConfig.config.getConfig("db");
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(db.getString("username"));
        dataSource.setPassword(db.getString("password"));
        dataSource.setUrl(db.getString("url"));
        dataSource.setDriverClassName(db.getString("driver"));
        return dataSource;
    }
}
