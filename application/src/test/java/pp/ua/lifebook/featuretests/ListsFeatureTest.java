package pp.ua.lifebook.featuretests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
// TODO @AndrewG: will be fixed in https://github.com/rockmanck/lifebook/projects/1#card-81596293
public class ListsFeatureTest {

    private String BASE_URL = "http://localhost:{port}/lists/1";
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        BASE_URL = BASE_URL.replace("{port}", String.valueOf(port));
    }

    @Test
    @DisplayName("User can remove item from list by item id")
    void testListItemRemoval() {
        HttpEntity<?> request = new HttpEntity<>("");
        final ResponseEntity<Void> response = restTemplate.exchange(BASE_URL + "/items/1", HttpMethod.DELETE, request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void assertItemIsRemoved(int listId, int itemId) {

    }
}
