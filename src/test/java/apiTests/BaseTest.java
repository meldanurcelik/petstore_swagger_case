package apiTests;

import io.restassured.RestAssured;
import io.restassured.http.*;
import io.restassured.response.Response;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;

public class BaseTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    // POST request
    public Response sendPostRequest(String requestBody) {
        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .extract().response();
    }

    // PUT request
    public Response sendPutRequest(String requestBody) {
        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/pet")
                .then()
                .extract().response();
    }

    // DELETE request
    public Response sendDeleteRequest(int petId) {
        return given()
                .when()
                .delete("/pet/" + petId)
                .then()
                .extract().response();
    }

    // GET request - int
    public Response sendGetRequest(int petId) {
        return given()
                .when()
                .get("/pet/" + petId)
                .then()
                .extract().response();
    }

    // GET request - string
    public Response sendGetRequestString(String petId) {
        return given()
                .when()
                .get("/pet/" + petId)
                .then()
                .extract().response();
    }

}