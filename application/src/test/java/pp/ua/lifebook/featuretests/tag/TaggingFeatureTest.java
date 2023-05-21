package pp.ua.lifebook.featuretests.tag;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pp.ua.lifebook.featuretests.FeatureTest;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.web.user.LbUserPrincipal;

import javax.sql.DataSource;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FeatureTest
@AutoConfigureMockMvc
public class TaggingFeatureTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DataSource dataSource;

    private MockMvc mvc;
    private JdbcTemplate jdbc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
        jdbc = new JdbcTemplate(dataSource);
    }

    @AfterEach
    void teardown() {
        jdbc.update("DELETE FROM tag WHERE 1=1");
    }

    private final int userId = 1;
    private final LbUserPrincipal user = new LbUserPrincipal(
        User.builder()
            .setLogin("user1")
            .setId(userId)
            .createUser()
    );

    @Test
    @DisplayName("When user does not have tags Then suggest returns nothing")
    void noTagSuggestion() throws Exception {
        mvc.perform(get("/tags/suggest?term=").with(user(user)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string("[]"));
    }

    @Test
    @DisplayName("""
    When request suggest for matching tags
     Then return case insensitive match for startWith and then contains sorted alphabetically
     AND suggest to create tag without exact match
    """)
    void matchStartWithAndContains() throws Exception {
        givenTags("Column", "Economy", "Work", "Ecology", "Collagen", "Paracol");

        mvc.perform(get("/tags/suggest?term=col").with(user(user)))
            .andExpect(status().is2xxSuccessful())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$", Matchers.hasSize(5)))
            .andExpect(jsonPath("$[0].label").value("Collagen"))
            .andExpect(jsonPath("$[1].label").value("Column"))
            .andExpect(jsonPath("$[2].label").value("Ecology"))
            .andExpect(jsonPath("$[3].label").value("Paracol"))
            .andExpect(jsonPath("$[4].label").value("col"))
            .andExpect(jsonPath("$[4].newTag").value(true));
    }

    private void givenTags(String... tags) {
        for (String tag : tags) {
            jdbc.update("INSERT INTO tag(user_id, name) VALUES(?, ?)", userId, tag);
        }
    }
}
