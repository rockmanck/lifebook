package org.acme;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {

    @TestHTTPResource
    String url;

    @DisplayName("Accepted name appears in output")
    @Test
    void testHelloEndpoint() {
        System.out.println(url);
        given()
          .when().get("/hello?name=Jango")
          .then()
             .statusCode(200)
             .body(is("Hello there, Jango"));
    }
}