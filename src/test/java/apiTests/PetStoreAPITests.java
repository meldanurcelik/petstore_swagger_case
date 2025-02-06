package apiTests;

import io.restassured.*;
import org.testng.annotations.*;

public class PetStoreAPITests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }
}
