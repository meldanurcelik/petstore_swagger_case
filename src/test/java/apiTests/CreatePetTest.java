package apiTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import static io.restassured.RestAssured.*;

public class CreatePetTest extends BaseTest { // BaseTest'ten extends edildi

    @Test
    public void testCreatePet() {
        String requestBody = "{\n" +
                "  \"id\": 12345,\n" +
                "  \"category\": { \"id\": 1, \"name\": \"Dog\" },\n" +
                "  \"name\": \"Buddy\",\n" +
                "  \"photoUrls\": [\"https://example.com/dog.jpg\"],\n" +
                "  \"tags\": [{ \"id\": 1, \"name\": \"friendly\" }],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200!");
        Assert.assertEquals(response.jsonPath().getString("name"), "Buddy", "Pet name does not match!");

    }
}