package apiTests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

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

        Response response = sendPostRequest(requestBody);

        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200!");
        Assert.assertEquals(response.jsonPath().getString("name"), "Buddy", "Pet name does not match!");
    }

    @Test
    public void testCreatePetWithMissingName() {
        String requestBody = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": { \"id\": 0, \"name\": \"string\" },\n" +
                "  \"name\": \"\",\n" +
                "  \"photoUrls\": [\"string\"],\n" +
                "  \"tags\": [{ \"id\": 0, \"name\": \"string\" }],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = sendPostRequest(requestBody);

        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200!");
    }

    @Test
    public void testCreatePetWithMissingStatus() {
        String requestBody = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": { \"id\": 0, \"name\": \"string\" },\n" +
                "  \"name\": \"Pet1\",\n" +
                "  \"photoUrls\": [\"url1\"],\n" +
                "  \"tags\": [{ \"id\": 0, \"name\": \"tag1\" }],\n" +
                "  \"status\": \"\"\n" +
                "}";

        Response response = sendPostRequest(requestBody);

        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200!");
    }

    @Test
    public void testCreatePetWithInvalidPhotoUrls() {
        String requestBody = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": { \"id\": 0, \"name\": \"string\" },\n" +
                "  \"name\": \"Pet1\",\n" +
                "  \"photoUrls\": [],\n" +
                "  \"tags\": [{ \"id\": 0, \"name\": \"tag1\" }],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = sendPostRequest(requestBody);

        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200!");
    }

    @Test
    public void testCreatePetWithInvalidId() {
        String requestBody = "{\n" +
                "  \"id\": -1,  // Geçersiz id\n" +
                "  \"category\": { \"id\": 0, \"name\": \"string\" },\n" +
                "  \"name\": \"Pet1\",\n" +
                "  \"photoUrls\": [\"url1\"],\n" +
                "  \"tags\": [{ \"id\": 0, \"name\": \"tag1\" }],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = sendPostRequest(requestBody);

        Assert.assertEquals(response.getStatusCode(), 400, "Status code is not 400!");
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

        Assert.assertEquals(response.getStatusCode(), 400, "Status code is not 400!");
    }

    @Test
    public void testCreatePetWithMissingCategory() {
        String requestBody = "{\n" +
                "  \"id\": 0,\n" +
                "  \"name\": \"Pet1\",\n" +
                "  \"photoUrls\": [\"url1\"],\n" +
                "  \"tags\": [{ \"id\": 0, \"name\": \"tag1\" }],\n" +
                "  \"status\": \"available\"  // Eksik category \n" +
                "}";

        Response response = sendPostRequest(requestBody);

        Assert.assertEquals(response.getStatusCode(), 400, "Status code is not 400!");
    }

}