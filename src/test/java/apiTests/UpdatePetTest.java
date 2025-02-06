package apiTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import static io.restassured.RestAssured.*;

public class UpdatePetTest extends BaseTest { // BaseTest'ten extends edildi

    @Test
    public void testUpdatePet() {
        // Güncellenecek pet bilgileri
        String updatedRequestBody = "{\n" +
                "  \"id\": 12345,\n" +
                "  \"category\": { \"id\": 1, \"name\": \"Dog\" },\n" +
                "  \"name\": \"Max\",\n" +  // "Buddy" olan isim "Max" olarak değiştirildi
                "  \"photoUrls\": [\"https://example.com/dog.jpg\"],\n" +
                "  \"tags\": [{ \"id\": 1, \"name\": \"friendly\" }],\n" +
                "  \"status\": \"sold\"\n" +  // Status "available" -> "sold" olarak güncellendi
                "}";

        // PUT isteği ile pet güncelleme
        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatedRequestBody)
                .when()
                .put("/pet")
                .then()
                .extract().response();

        // HTTP Status kodunun 200 olup olmadığını doğrula
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200!");

        // Güncellenen pet adını doğrula
        String updatedPetName = response.jsonPath().getString("name");
        Assert.assertEquals(updatedPetName, "Max", "Updated pet name does not match!");

        // Güncellenen pet durumunu doğrula
        String updatedPetStatus = response.jsonPath().getString("status");
        Assert.assertEquals(updatedPetStatus, "sold", "Updated pet status does not match!");
    }
}
