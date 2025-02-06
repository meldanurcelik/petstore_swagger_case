package apiTests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeletePetTest extends BaseTest { // BaseTest'ten extends edildi

    @Test
    public void testDeletePet() {
        int petId = 12345;  // Daha önce eklediğimiz pet'in ID'si

        // DELETE isteği ile pet'i silme
        Response response = given()
                .when()
                .delete("/pet/" + petId)
                .then()
                .extract().response();

        // HTTP Status kodunun 200 olup olmadığını doğrula
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200!");

        // Pet silindikten sonra tekrar GET isteği atıp olmadığını doğrulayalım
        Response getResponse = given()
                .when()
                .get("/pet/" + petId)
                .then()
                .extract().response();

        // Pet'in bulunamaması için HTTP 404 dönmeli
        Assert.assertEquals(getResponse.getStatusCode(), 404, "Deleted pet still exists!");

    }
}