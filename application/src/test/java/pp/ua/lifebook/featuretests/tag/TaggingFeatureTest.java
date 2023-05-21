package pp.ua.lifebook.featuretests.tag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import pp.ua.lifebook.featuretests.FeatureTest;

import javax.sql.DataSource;

@FeatureTest
@AutoConfigureMockMvc
public class TaggingFeatureTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("test")
    void canRun() {

    }
}
