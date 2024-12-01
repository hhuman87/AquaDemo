package apiTests.reqres;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetTest {

    @Test
    public void getTest() {
        baseURI = "https://reqres.in/api";

        given().
                get("/users?page=2").
        then().
                statusCode(200).
                body("data[4].first_name",equalTo("George")).
                body("data.first_name", hasItems("George", "Rachel")).
                log().all();
    }
}
