package pl.edu.pjwstk.jaz;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@IntegrationTest
public class AuthenticationTest {

    @BeforeClass
    public static void beforeClass() throws  Exception{
        // @formatter:off
        given()
                .body(new RegisterRequest("admin","admin123")) //admin
                .contentType(ContentType.JSON)
        .when()
                .post("/api/register")
        .thenReturn();


        given()
                .body(new RegisterRequest("user","user123")) //user
                .contentType(ContentType.JSON)
        .when()
                .post("/api/register")
        .thenReturn();
        // @formatter:on
    }

    @Test
    public void tryToRegisterUserShouldResponseStatus200(){
        // @formatter:off
        given()
        .when()
                .body(new LoginRequest("adam","123456"))
                .contentType(ContentType.JSON)
                .post("/api/register")
        .then()
                .statusCode(200);
        // @formatter:on
    }

    @Test
    public void tryToRegisterUserWithAlreadyExistUsernameShouldResponseStatus500(){
        // @formatter:off
        given()
        .when()
                .body(new LoginRequest("admin","adminnn"))
                .contentType(ContentType.JSON)
                .post("/api/register")
        .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        // @formatter:on
    }

    @Test
    public void tryToLoginRegisteredUserShouldResponseStatus200(){
        // @formatter:off
        given()
        .when()
                .body(new LoginRequest("admin","admin123"))
                .contentType(ContentType.JSON)
                .post("/api/login")
        .then()
                .statusCode(200);
        // @formatter:on
    }

    @Test
    public void tryToLoginUnregisteredUserShouldResponseStatus500(){
        // @formatter:off
        given()
        .when()
                .body(new LoginRequest("adam","adasdasdsa"))
                .contentType(ContentType.JSON)
                .post("/api/login")
        .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        // @formatter:on

    }

    @Test
    public void loginUserAdminAndGetAccessToEndPointOnlyForAdminShouldResponseStatus200(){
        // @formatter:off
       var response =
       given()
       .when()
                .body(new LoginRequest("admin","admin123"))
                .contentType(ContentType.JSON)
                .post("/api/login")
       .thenReturn();

       given()
               .cookies(response.getCookies())
               .get("/api/onlyAdmin")
       .then()
               .statusCode(200);
        // @formatter:on
    }
    @Test
    public void loginDefaultUserAndCheckHisAccessToOnlyAdminShouldResponseStatus403(){
        // @formatter:off
        var response =
         given()
         .when()
                .body(new LoginRequest("user","user123"))
                .contentType(ContentType.JSON)
                .post("/api/login")
         .thenReturn();

        given()
                .cookies(response.getCookies())
                .get("/api/onlyAdmin")
        .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
        // @formatter:on
    }
    @Test
    public void loginDefaultUserAndCheckHisAccessToReadShouldResponseStatus200(){
        // @formatter:off
        var response =
        given()
        .when()
                .body(new LoginRequest("user","user123"))
                .contentType(ContentType.JSON)
                .post("/api/login")
        .thenReturn();

        given()
                .cookies(response.getCookies())
                .get("/api/read")
        .then()
                .statusCode(200);
        // @formatter:on
    }
    @Test
    public void loginAdminUserAndCheckHisAccessToReadShouldResponseStatus200(){
        // @formatter:off
        var response =
        given()
        .when()
                .body(new LoginRequest("admin","admin123"))
                .contentType(ContentType.JSON)
                .post("/api/login")
        .thenReturn();

        given()
                .cookies(response.getCookies())
                .get("/api/read")
        .then()
                .statusCode(200);
        // @formatter:on
    }
    @Test
    public void loginUserAccessToIsReadyResponseStatus200(){
        // @formatter:off
        var response =
        given()
        .when()
                .body(new LoginRequest("admin","admin123"))
                .contentType(ContentType.JSON)
                .post("/api/login")
        .thenReturn();

        given()
                .cookies(response.getCookies())
                .get("/api/auth0/is-ready")
        .then()
                .statusCode(200);
        // @formatter:on
    }
    @Test
    public void accessToPathAuth0withLoginUserShouldResponseStatus200() {
        // @formatter:off
        var response =
        given()
        .when()
                .body(new LoginRequest("admin","admin123"))
                .contentType(ContentType.JSON)
                .post("/api/login")
        .thenReturn();

        given()
                .cookies(response.getCookies())
                .get("/api/auth0/filterUse")
        .then()
                .statusCode(200);
        // @formatter:on
    }
    @Test
    public void accessToPathAuth0WithoutLoggedUserShouldResponseStatusUnauthorised() {
        // @formatter:off
        given()
                .get("/api/auth0/filterUse")
        .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
        // @formatter:on
    }
}
