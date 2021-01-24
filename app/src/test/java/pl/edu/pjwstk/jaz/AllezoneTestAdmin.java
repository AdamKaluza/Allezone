package pl.edu.pjwstk.jaz;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.Request.CategoryRequest;
import pl.edu.pjwstk.jaz.Request.SectionRequest;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@IntegrationTest
public class AllezoneTestAdmin {
    private static   Response adminResponse;

    @BeforeClass
    public static void beforeClass() throws Exception {
        // @formatter:off
        given()
                .body(new RegisterRequest("admin","admin123")) //user
                .contentType(ContentType.JSON)
                .when()
                .post("/api/register")
                .thenReturn();
        adminResponse = given()
                .body(new LoginRequest("admin","admin123")) //admin
                .contentType(ContentType.JSON)
                .when()
                .post("/api/login")
                .thenReturn();
        // @formatter:on
    }


    @Test
    public void createSectionShouldResponseStatus200() {
        // @formatter:off

            given()
                    .cookies(adminResponse.getCookies())
                    .body(new SectionRequest("Garden"))
                    .contentType(ContentType.JSON)
                    .post("/api/addSection")
            .then()
                    .statusCode(200);
            // @formatter:on

    }
    @Test
    public void createCategoryShouldResponseStatus200(){
        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Home"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("Furniture","Home"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .then()
                .statusCode(200);
        // @formatter:on
    }
    @Test
    public void tryToCreateSectionWithAllReadyExistNameShouldResponseStatus500(){
        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Electronic"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Electronic"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .then()
                .statusCode(500);
        // @formatter:on
    }
    @Test
    public void tryToCreateCategoryWithAllReadyExistNameShouldResponseStatus500(){
        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Electronic"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("Sport","Electronic"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("Sport","Electronic"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .then()
                .statusCode(500);
        // @formatter:on
    }
//
    @Test
    public void updateSectionNameShouldResponseStatus200(){
        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                //  .when()
                .body(new SectionRequest("Electronic"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
               // .when()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Health"))
                .contentType(ContentType.JSON)
                .put("/api/updateSection/Electronic")
                .then()
                .statusCode(200);
        // @formatter:on

    }
    @Test
    public void updateCategoryNameShouldResponseStatus200(){
        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Electronic"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                //  .when()
                .body(new CategoryRequest("Sport","Electronic"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                //.when()
                .body(new CategoryRequest("Tv","Electronic"))
                .contentType(ContentType.JSON)
                .put("/api/updateCategory/Sport")
                .then()
                .statusCode(200);
        // @formatter:on

    }

}
