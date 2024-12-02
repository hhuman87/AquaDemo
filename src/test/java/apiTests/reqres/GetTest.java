package apiTests.reqres;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetTest {

    private Integer userID;

    @Test
    public void getUserIDTest() {
        baseURI = "https://reqres.in/api";
        getUserID();
        validateUserID();
    }

    public void getUserID() {
        Response response =
            given()
                .get("/users?page=2").
            then()
                .statusCode(200)
                .body("page",equalTo(2))
                .body("data[4].first_name",equalTo("George"))
                .body("data.first_name", hasItems("George", "Rachel"))
            .extract().response();

        userID = response.path("data[1].id");
        System.out.println("User ID is: "+userID);
    }

    public void validateUserID() {
        given()
                .get("/users?page=2").
        then()
                .assertThat()
                .body("data[1].id",equalTo(userID));
    }
}
