package apiTests.reqres;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class PostTest {

    private String token;
    private final String contentType = "application/json; charset=utf-8";

    @Test
    public void postUser() {
        baseURI = "https://reqres.in/api";
        getUserToken();
        validateLoginSuccessful();
    }

    public void getUserToken() {
        Response response =
            given()
                .header("Content-Type", contentType)
                .body("{" +
                        "    \"email\": \"eve.holt@reqres.in\"," +
                        "    \"password\": \"pistol\"\n" +
                        "}")
            .when()
                .post("/register")
            .then()
                .statusCode(200)
            .extract().response();

        token = response.path("token");
        System.out.println("Token is: "+token);
    }

    public void validateLoginSuccessful() {
        Response response =
            given()
                .header("Content-Type", contentType)
                .body("{" +
                        "    \"email\": \"eve.holt@reqres.in\"," +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
            .when()
                .post("/login")
            .then()
                .statusCode(200)
            .extract().response();

        String expectedToken = response.path("token");
        System.out.println("Expected token is: "+expectedToken);
        Assertions.assertEquals(expectedToken, token);
    }
}
