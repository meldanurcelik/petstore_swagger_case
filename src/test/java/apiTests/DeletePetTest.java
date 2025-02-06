package apiTests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeletePetTest extends BaseTest { // BaseTest'ten extends edildi

    @Test
    public void testDeletePet() {
        // Önceden eklenmiş bir pet'i silme
        int petId = 12345; // Önceden eklenmiş bir pet ID'si

        Response response = sendDeleteRequest(petId);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200!");

        // Silinen pet'in sistemde olmadığını kontrol etme
        Response getResponse = sendGetRequest(petId);
        Assert.assertEquals(getResponse.getStatusCode(), 404, "Deleted pet still exists!");
    }

    @Test
    public void testDeleteNonExistentPet() {
        // Sistemde olmayan bir pet'i silme
        int nonExistentPetId = 99999; // Sistemde olmayan bir ID

        Response response = sendDeleteRequest(nonExistentPetId);
        Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 when deleting non-existent pet!");
    }

    @Test
    public void testDeletePetTwice() {
        // Aynı pet'i iki kez silme
        int petId = 12345;  // Daha önce eklenen pet'in ID'si

        Response firstDeleteResponse = sendDeleteRequest(petId); // İlk silme isteği
        Response secondDeleteResponse = sendDeleteRequest(petId); // İkinci silme isteği
        Assert.assertEquals(secondDeleteResponse.getStatusCode(), 404, "Expected 404 when deleting already deleted pet!");
    }

    @Test
    public void testDeletePetWithInvalidId() {
        // Geçersiz bir pet ID'si ile silme
        String invalidPetId = "abc123";  // Geçersiz bir ID formatı

        Response response = sendGetRequestString(invalidPetId);
        Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 when deleting pet with invalid ID!");
    }

    @Test
    public void testDeletePetWithEmptyId() {
        // Boş bir pet ID'si ile silme
        String emptyPetId = ""; // Boş ID gönderme

        Response response = sendGetRequestString(emptyPetId);
        Assert.assertEquals(response.getStatusCode(), 405, "Expected 405 when deleting pet with empty ID!");
    }

}