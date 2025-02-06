package apiTests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

public class CreatePetTest extends BaseTest { // BaseTest'ten extends edildi

    @Test
    public void testCreatePet() {
        // Pet verisini başarılı bir şekilde oluşturma
        String requestBody = "{\n" +
                "  \"id\": 12345,\n" +
                "  \"category\": { \"id\": 1, \"name\": \"Dog\" },\n" +
                "  \"name\": \"Buddy\",\n" +
                "  \"photoUrls\": [\"https://example.com/dog.jpg\"],\n" +
                "  \"tags\": [{ \"id\": 1, \"name\": \"friendly\" }],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = sendPostRequest(requestBody);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200!");

        // Oluşturulan pet verisinin doğru olup olmadığını kontrol etme
        Assert.assertEquals(response.jsonPath().getString("name"), "Buddy", "Pet name does not match!");
    }

    @Test
    public void testCreatePetWithMissingName() {
        // Boş pet adı ile oluşturma
        String requestBody = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": { \"id\": 0, \"name\": \"string\" },\n" +
                "  \"name\": \"\",\n" +
                "  \"photoUrls\": [\"string\"],\n" +
                "  \"tags\": [{ \"id\": 0, \"name\": \"string\" }],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = sendPostRequest(requestBody);

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 when pet name is empty!");

        // Pet'in adının boş olduğunu kontrol etme
        String petName = response.jsonPath().getString("name");
        Assert.assertTrue(petName == null || petName.isEmpty(), "Pet name should be empty or null!");
    }

    @Test
    public void testCreatePetWithMissingStatus() {
        // Boş status ile pet oluşturma
        String requestBody = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": { \"id\": 0, \"name\": \"string\" },\n" +
                "  \"name\": \"Pet1\",\n" +
                "  \"photoUrls\": [\"url1\"],\n" +
                "  \"tags\": [{ \"id\": 0, \"name\": \"tag1\" }],\n" +
                "  \"status\": \"\"\n" +
                "}";

        Response response = sendPostRequest(requestBody);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 when status is empty!");

        // Pet'in status'unun boş olduğunu kontrol etme
        String petStatus = response.jsonPath().getString("status");
        Assert.assertTrue(petStatus == null || petStatus.isEmpty(), "Pet status should be empty or null!");
    }

    @Test
    public void testCreatePetWithInvalidPhotoUrls() {
        // Geçersiz fotoğraf URL'leri ile pet oluşturma
        String requestBody = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": { \"id\": 0, \"name\": \"string\" },\n" +
                "  \"name\": \"Pet1\",\n" +
                "  \"photoUrls\": [],\n" +
                "  \"tags\": [{ \"id\": 0, \"name\": \"tag1\" }],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = sendPostRequest(requestBody);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 when photo URLs are empty!");

        // Pet'in fotoğraf URL'lerinin boş olduğunu kontrol etme
        String photoUrls = response.jsonPath().getString("photoUrls");
        Assert.assertTrue(photoUrls.equals("[]"), "Pet photo URLs should be empty or null!");
    }

    @Test
    public void testCreatePetWithInvalidId() {
        String requestBody = "{\n" +
                "  \"id\": \"a\",\n" +
                "  \"category\": { \"id\": 1, \"name\": \"string\" },\n" +
                "  \"name\": \"Pet1\",\n" +
                "  \"photoUrls\": [\"url1\"],\n" +
                "  \"tags\": [{ \"id\": 0, \"name\": \"tag1\" }],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = sendPostRequest(requestBody);
        Assert.assertEquals(response.getStatusCode(), 500, "Expected status code 500 when ID is invalid!");

        // Pet ID'sinin boş olduğunu kontrol etme
        String petId = response.jsonPath().getString("id");
        Assert.assertTrue(petId == null || petId.isEmpty(), "Pet ID should be empty or null!");
    }

    @Test
    public void testCreatePetWithInvalidJsonFormat() {
        String requestBody = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": { \"id\": 0, \"name\": \"string\" },\n" +
                "  \"name\": \"Pet1\",\n" +
                "  \"photoUrls\": [\"url1\"],\n" +
                "  \"tags\": [{ \"id\": 0, \"name\": \"tag1\" }],\n" +
                "  \"status\": \"available\", \n" +
                "}";

        Response response = sendPostRequest(requestBody);
        Assert.assertEquals(response.getStatusCode(), 400, "Expected status code 400 for invalid JSON format!");
    }

    @Test
    public void testCreatePetWithMissingCategory() {
        String requestBody = "{\n" +
                "  \"id\": 0,\n" +
                "  \"name\": \"Pet1\",\n" +
                "  \"photoUrls\": [\"url1\"],\n" +
                "  \"tags\": [{ \"id\": 0, \"name\": \"tag1\" }],\n" +
                "  \"status\": \"available\", \n" +
                "}";

        Response response = sendPostRequest(requestBody);
        Assert.assertEquals(response.getStatusCode(), 400, "Expected status code 400 when category is missing!");

        // Pet kategorisinin boş olduğunu kontrol etme
        String category = response.jsonPath().getString("category");
        Assert.assertTrue(category == null || category.isEmpty(), "Pet category should be empty or null!");
    }

}