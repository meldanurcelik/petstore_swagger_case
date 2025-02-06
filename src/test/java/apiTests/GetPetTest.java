package apiTests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import static io.restassured.RestAssured.*;

public class GetPetTest extends BaseTest { // BaseTest'ten extends edildi

    @Test
    public void testGetPetById() {
        // Pet verisini başarılı bir şekilde listeleme
        int petId = 12345; // Var olan ID

        Response response = sendGetRequest(petId);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200!");

        // Oluşturulan pet verisinin doğru olup olmadığını kontrol etme
        Assert.assertEquals(response.jsonPath().getString("name"), "Buddy", "Pet name does not match!");
    }

    @Test
    public void testGetNonExistentPet() {
        // Var olmayan pet verisini listeleme
        int nonExistentPetId = 99999; // Var olmayan ID

        Response response = sendGetRequest(nonExistentPetId);
        Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 when getting non-existent pet!");

        // Hata mesajının doğru olup olmadığını kontrol etme
        String errorMessage = response.jsonPath().getString("message");
        Assert.assertEquals(errorMessage, "Pet not found", "Error message does not match!");
    }

    @Test
    public void testGetPetWithInvalidId() {
        // Geçersiz ID ile pet verisini listeleme
        String invalidPetId = "abc123"; // Geçersiz ID

        Response response = sendGetRequestString(invalidPetId);
        Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 when getting pet with invalid ID!");

        // Hata mesajının doğru olup olmadığını kontrol etme
        String errorMessage = response.jsonPath().getString("message");
        Assert.assertEquals(errorMessage, "java.lang.NumberFormatException: For input string: \"" + invalidPetId + "\"", "Error message does not match!");
    }

    @Test
    public void testGetPetWithEmptyId() {
        // Boş ID ile pet verisini listeleme
        String emptyPetId = ""; // Boş ID

        Response response = sendGetRequestString(emptyPetId);
        Assert.assertEquals(response.getStatusCode(), 405, "Expected 405 when getting pet with empty ID!");
    }

}