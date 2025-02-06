package apiTests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import static io.restassured.RestAssured.*;

public class GetPetTest extends BaseTest { // BaseTest'ten extends edildi

    @Test
    public void testGetPetById() {
        int petId = 12345;

        Response response = given()
                .when()
                .get("/pet/" + petId)
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200!");
        Assert.assertEquals(response.jsonPath().getString("name"), "Buddy", "Pet name does not match!");

    }
}