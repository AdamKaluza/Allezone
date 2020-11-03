package pl.edu.pjwstk.jaz;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@IntegrationTest
public class AverageIT {
    @Test
    public void when_no_parametr_supplied_should_print_a_message() {
        // @formatter:off
         given()
         .when()
                .get("/api/average")
         .then()
               //  .contentType(ContentType.JSON)
                .body("message",equalTo("Please put parameters."));
         //@formatter:on
    }

    @Test
    public void should_remove_trailing_zero_case_1() {
        // @formatter:off
        given()
        .when()
                .param("numbers","1,2")
                .get("/api/average")
        .then()
                .body("message",equalTo("Average equals: 1.5"));
        //@formatter:on
    }

    @Test
    public void should_remove_trailing_zero_case_2() {
        // @formatter:off
        given()
        .when()
                .param("numbers","2,1,1")
                .get("/api/average")
        .then()
                .body("message",equalTo("Average equals: 1.33"));
        //@formatter:on
    }

    @Test
    public void should_remove_trailing_zero_case_3() {
        // @formatter:off
        given()
        .when()
                .param("numbers","4,3,1,7,5")
                .get("/api/average")
        .then()
                .body("message",equalTo("Average equals: 4"));
        //@formatter:on
    }

}
