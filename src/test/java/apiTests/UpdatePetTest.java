package apiTests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

public class UpdatePetTest extends BaseTest { // BaseTest'ten extends edildi

    @Test
    public void testUpdatePet() {
        // Pet verisini başarılı bir şekilde güncelleme
        String updatedRequestBody = "{\n" +
                "  \"id\": 12345,\n" +
                "  \"category\": { \"id\": 1, \"name\": \"Dog\" },\n" +
                "  \"name\": \"Maxx\",\n" +  // "Buddy" olan isim "Max" olarak değiştirildi
                "  \"photoUrls\": [\"https://example.com/dog.jpg\"],\n" +
                "  \"tags\": [{ \"id\": 1, \"name\": \"friendly\" }],\n" +
                "  \"status\": \"sold\"\n" +  // Status "available" -> "sold" olarak güncellendi
                "}";

        Response response = sendPutRequest(updatedRequestBody);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200!");

        // Güncellenen pet verisinin doğru olup olmadığını kontrol etme
        String updatedPetName = response.jsonPath().getString("name");
        Assert.assertEquals(updatedPetName, "Maxx", "Updated pet name does not match!");
        String updatedPetStatus = response.jsonPath().getString("status");
        Assert.assertEquals(updatedPetStatus, "sold", "Updated pet status does not match!");
    }

    @Test
    public void testUpdatePetWithMissingId() {
        // Eksik ID ile güncelleme yapma
        String updatedRequestBody = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": { \"id\": 1, \"name\": \"Dog\" },\n" +
                "  \"name\": \"Buddy Updated\",\n" +
                "  \"photoUrls\": [\"https://example.com/dog_updated.jpg\"],\n" +
                "  \"tags\": [{ \"id\": 1, \"name\": \"friendly\" }],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = sendPutRequest(updatedRequestBody);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 when missing ID!");

        // Güncellenen pet verisinin doğru olup olmadığını kontrol etme
        String missingPetName = response.jsonPath().getString("name");
        Assert.assertEquals(missingPetName, "Buddy Updated", "Pet name was not updated correctly!");
    }

    @Test
    public void testUpdatePetWithInvalidJsonFormat() {
        // Geçersiz JSON formatı ile güncelleme yapma
        String invalidRequestBody = "{\n" +
                "  \"id\": 12345,\n" +
                "  \"category\": { \"id\": 1, \"name\": \"Dog\" },\n" +
                "  \"name\": \"Buddy Updated\",\n" +
                "  \"photoUrls\": [\"https://example.com/dog_updated.jpg\"],\n" +
                "  \"tags\": [{ \"id\": 1, \"name\": \"friendly\" }],\n" +
                "  \"status\": \"available\", \n" +
                "}";

        Response response = sendPutRequest(invalidRequestBody);
        Assert.assertEquals(response.getStatusCode(), 400, "Expected status code 400 when invalid JSON format!");
    }

    @Test
    public void testUpdatePetStatus() {
        // Sadece status alanını güncelleme
        // Öncelikle bir pet oluşturuluyor
        String requestBody = "{\n" +
                "  \"id\": 12345,\n" +
                "  \"category\": { \"id\": 1, \"name\": \"Dog\" },\n" +
                "  \"name\": \"Buddy\",\n" +
                "  \"photoUrls\": [\"https://example.com/dog.jpg\"],\n" +
                "  \"tags\": [{ \"id\": 1, \"name\": \"friendly\" }],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response createResponse = sendPostRequest(requestBody);
        Assert.assertEquals(createResponse.getStatusCode(), 200, "Expected status code 200!");

        // Sadece status alanını güncelleme
        String updatedRequestBody = "{\n" +
                "  \"id\": 12345,\n" +
                "  \"category\": { \"id\": 1, \"name\": \"Dog\" },\n" +
                "  \"name\": \"Buddy\",\n" +
                "  \"photoUrls\": [\"https://example.com/dog.jpg\"],\n" +
                "  \"tags\": [{ \"id\": 1, \"name\": \"friendly\" }],\n" +
                "  \"status\": \"sold\"\n" +
                "}";

        Response updateResponse = sendPutRequest(updatedRequestBody);
        Assert.assertEquals(updateResponse.getStatusCode(), 200, "Expected status code 200!");

        // Güncellenen pet verisinin doğru olup olmadığını kontrol etme
        Assert.assertEquals(updateResponse.jsonPath().getString("status"), "sold", "Status was not updated correctly!");
    }

}