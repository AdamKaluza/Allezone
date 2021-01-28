package pl.edu.pjwstk.jaz;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;
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
        List<PhotoRequest> photoRequestList = getPhotoRequestList();

        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Home"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("UsedFurniture","Home"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "Kanapa lekko używana",
                        "Na sprzedaż kanapa taka jak na zdjęciach",
                        "UsedFurniture",
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
    public void creatingNewAuctionWhereTitleIsNullResponse500(){
        List<PhotoRequest> photoRequestList = getPhotoRequestList();

        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Home"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("UsedFurniture","Home"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        null,
                        "Na sprzedaż kanapa taka jak na zdjęciach",
                        "UsedFurniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .then()
                .statusCode(500);

        // @formatter:on

    }
    @Test
    public void getAuctionShouldResponse200(){
        List<PhotoRequest> photoRequestList = getPhotoRequestList();

        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Home"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("UsedFurniture","Home"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "kanapa",
                        "Na sprzedaż kanapa taka jak na zdjęciach",
                        "UsedFurniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .get("/api/auctions/5")
                .then()
                .statusCode(200);

        // @formatter:on

    }
    @Test
    public void getAuctionListShouldResponse200(){
        List<PhotoRequest> photoRequestList = getPhotoRequestList();

        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Home"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("UsedFurniture","Home"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1233,
                        "Mydło",
                        "Na sprzedaż mydło",
                        "UsedFurniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "kanapa",
                        "Na sprzedaż kanapa taka jak na zdjęciach",
                        "UsedFurniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .get("/api/auctions")
                .then()
                .statusCode(200);

        // @formatter:on

    }




    @Test
    public void editAuctionTitleShouldResponseStatus200(){
        List<PhotoRequest> photoRequestList = getPhotoRequestList();

        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("HomeAndGarden"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("Furniture","HomeAndGarden"))
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
    @Test
    public void editAuctionByNotCreatorShouldResponseStatus401(){
        List<PhotoRequest> photoRequestList = getPhotoRequestList();

        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("home"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("furniture","home"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "Kanapa lekko używana",
                        "Na sprzedaż kanapa taka jak na zdjęciach",
                        "furniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new AuctionRequest(0,
                        "Kanapa lekko uzywana. Zakupiona Rok temu.",
                        null,
                        null,
                        null,
                        2L,
                        null,
                        null
                ))
                .contentType(ContentType.JSON)
                .put("/api/editAuction/5")
                .then()
                .statusCode(401);

        // @formatter:on
    }
    @Test
    public void editAuctionByNotCreateponseStatus401(){
        List<PhotoRequest> photoRequestList = getPhotoRequestList();
        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("home"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("furniture","home"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "Kanapa lekko używana",
                        "Na sprzedaż kanapa taka jak na zdjęciach",
                        "furniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new AuctionRequest(0,
                        "Kanapa lekko uzywana. Zakupiona Rok temu.",
                        null,
                        null,
                        null,
                        2L,
                        null,
                        null
                ))
                .contentType(ContentType.JSON)
                .put("/api/editAuction/5")
                .then()
                .statusCode(401);

        // @formatter:on
    }
    @Test
    public void checkingVersionShouldResponse500() {
        List<PhotoRequest> photoRequestList = getPhotoRequestList();


        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Dom"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("meble","Dom"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "Kanapa lekko używana",
                        "Na sprzedaż kanapa taka jak na zdjęciach",
                        "meble",
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
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(0,
                        "Kanapa lekko uzywana. Zakupiona Rok temu.",
                        null,
                        null,
                        null,
                        2L,
                        null,
                        null
                ))
                .contentType(ContentType.JSON)
                .put("/api/editAuction/5")
                .then()
                .statusCode(200);

    }


    @NotNull
    private List<PhotoRequest> getPhotoRequestList() {
        List<PhotoRequest> photoRequestList = new ArrayList<>();
        photoRequestList.add(new PhotoRequest("link1", 1));
        photoRequestList.add(new PhotoRequest("link2", 2));
        photoRequestList.add(new PhotoRequest("link3", 3));
        return photoRequestList;
    }

    @NotNull
    private List<ParameterRequest> getParameterRequestList() {
        List<ParameterRequest> parameterRequestList = new ArrayList<>();
        parameterRequestList.add(new ParameterRequest("Szerokosc", "187cm"));
        parameterRequestList.add(new ParameterRequest("Wysokosc", "98cm"));
        parameterRequestList.add(new ParameterRequest("dlugosc", "121cm"));
        return parameterRequestList;
    }

}
