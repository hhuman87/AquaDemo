package apiTests.reqres;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateUpdateDeleteTest {

    private final String contentType = "application/json; charset=utf-8";

    LocalDate dateToday = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String today = dateToday.format(formatter);

    @Test
    public void createUpdateDeleteUser() {
        baseURI = "https://reqres.in/api";
        createUser();
        updateUser();
        deleteUser();
    }

    public void createUser() {
        Response response =
            given()
                .header("Content-Type", contentType)
                .body("{" +
                        "    \"name\": \"morpheus\"," +
                        "    \"job\": \"leader\"\n" +
                        "}")
            .when()
                .post("/users")
            .then()
                .statusCode(201)
            .extract().response();

        String userName = response.path("name");
        System.out.println("User name: "+userName);
        Assertions.assertEquals("morpheus",userName);

        String userJob = response.path("job");
        System.out.println("User job: "+userJob);
        Assertions.assertEquals("leader",userJob);

        String createdDate = response.path("createdAt");
        System.out.println("Created date: "+createdDate);
        assertTrue(createdDate.contains(today),"Should contain created date");
    }

    public void updateUser() {
        Response response =
            given()
                .header("Content-Type", contentType)
                .body("{" +
                        "    \"name\": \"morpheus\"," +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
            .when()
                .put("/users/2")
            .then()
                .statusCode(200)
            .extract().response();

        String userName = response.path("name");
        System.out.println("User name: "+userName);
        Assertions.assertEquals("morpheus",userName);

        String userJob = response.path("job");
        System.out.println("User job: "+userJob);
        Assertions.assertEquals("zion resident",userJob);

        String updatedDate = response.path("updatedAt");
        System.out.println("Updated date: "+updatedDate);
        assertTrue(updatedDate.contains(today),"Should contain updated date");
    }

    public void deleteUser() {
        given()
        .when()
            .delete("/users/2")
        .then()
            .statusCode(204)
            .assertThat()
            .body(equalTo(""));

        System.out.println("User is deleted.");
    }
}
