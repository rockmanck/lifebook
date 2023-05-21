package pp.ua.lifebook.featuretests.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class TestDbConfig {

    @Bean
    @Lazy
    EmbeddedPostgresDb embeddedPostgresDb() {
        return new EmbeddedPostgresDb();
    }

    @Bean
    @Primary
    DataSource testDataSource(EmbeddedPostgresDb embeddedPostgresDb) {
        return embeddedPostgresDb.getDataSource();
    }
}
