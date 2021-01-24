package pl.edu.pjwstk.jaz;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.Request.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;


@RunWith(SpringRunner.class)
@IntegrationTest
public class AllezoneTestUser {

    private static Response adminResponse;
    private static Response userResponse;

    @BeforeClass
    public static void beforeClass() throws Exception {
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
        adminResponse = given()
                .body(new LoginRequest("admin","admin123"))
                .contentType(ContentType.JSON)
                .when()
                .post("/api/login")
                .thenReturn();
        userResponse = given()
                .body(new LoginRequest("user","user123"))
                .contentType(ContentType.JSON)
                .when()
                .post("/api/login")
                .thenReturn();
        // @formatter:on
    }

    @Test
    public void creatingNewAuctionShouldResponseStatus200(){
        List<PhotoRequest> photoRequestList = new ArrayList<>();
        photoRequestList.add(new PhotoRequest("link1",1));
        photoRequestList.add(new PhotoRequest("link2",2));
        photoRequestList.add(new PhotoRequest("link3",3));

        List<ParameterRequest> parameterRequestList = new ArrayList<>();
        parameterRequestList.add(new ParameterRequest("Szerokosc","187cm"));
        parameterRequestList.add(new ParameterRequest("Wysokosc","98cm"));
        parameterRequestList.add(new ParameterRequest("dlugosc","121cm"));

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
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "Kanapa lekko używana",
                        "Na sprzedaż kanapa taka jak na zdjęciach",
                        "Furniture",
                        photoRequestList,
                        parameterRequestList
                        ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .then()
                .statusCode(200);

        // @formatter:on

    }
    @Test
    public void editAuctionTitleShouldResponseStatus200(){
        List<PhotoRequest> photoRequestList = new ArrayList<>();
        photoRequestList.add(new PhotoRequest("link1",1));
        photoRequestList.add(new PhotoRequest("link2",2));
        photoRequestList.add(new PhotoRequest("link3",3));

        List<ParameterRequest> parameterRequestList = new ArrayList<>();
        parameterRequestList.add(new ParameterRequest("Szerokosc","187cm"));
        parameterRequestList.add(new ParameterRequest("Wysokosc","98cm"));
        parameterRequestList.add(new ParameterRequest("dlugosc","121cm"));

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
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "Kanapa lekko używana",
                        "Na sprzedaż kanapa taka jak na zdjęciach",
                        "Furniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(0,
                        "Kanapa lekko uzywana. Zakupiona Rok temu.",
                        null,
                        null,
                        null,
                        1L,
                        null,
                        null
                ))
                .contentType(ContentType.JSON)
                .put("/api/editAuction/5")
                .then()
                .statusCode(200);

        // @formatter:on
    }


}
