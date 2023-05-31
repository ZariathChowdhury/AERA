import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;


public class GetPlace {
    private JsonPath jsonPath;
    private ResponseBody body;
    private RequestSpecification httpRequest;
    private String baseURI =  "https://rahulshettyacademy.com";
    private String resource = "/maps/api/place/get/json";

    @Before
    public void Place()
    {
        // Using Rest-Assured class to set up a request
        RestAssured.baseURI = baseURI;
        // Getting the RequestSpecification of the request
        httpRequest = RestAssured.given()
                .queryParam("key", "qaclick123")
                .queryParam("place_id","928b51f64aed18713b0d164d9be8d67f" );
        //Retrieving the response body using getBody() method
        Response response = httpRequest.get(resource);

        // Get JSON Representation from Response Body
        body = response.getBody();
        jsonPath = response.jsonPath();
    }

    @Test
    public void verifyFieldsNotEmpty()
    {
        Double lat = jsonPath.getDouble("location.lat");
        MatcherAssert.assertThat(lat, is(instanceOf(Double.class)));

        Double lng = jsonPath.getDouble("location.lng");
        MatcherAssert.assertThat(lng, is(instanceOf(Double.class)));

        String accuracy = jsonPath.get("accuracy");
        MatcherAssert.assertThat(accuracy, is(not(emptyString())));

        String name = jsonPath.get("name");
        MatcherAssert.assertThat(name, is(not(emptyString())));

        String phone = jsonPath.get("phone_number");
        MatcherAssert.assertThat(phone, is(not(emptyString())));

        String address = jsonPath.get("address");
        MatcherAssert.assertThat(address, is(not(emptyString())));

        String website = jsonPath.get("website");
        MatcherAssert.assertThat(website, is(not(emptyString())));

        String language = jsonPath.get("language");
        MatcherAssert.assertThat(language, is(not(emptyString())));

        String types = jsonPath.get("types[0]");
        MatcherAssert.assertThat(types, is(not(emptyString())));
    }

    @Test
    public void verifyKeyExists()
    {
        httpRequest.when().get(resource)
                .then()
                .body("$",hasKey("location.lat"))
                .body("$",hasKey("location.lng"))
                .body("$",hasKey("accuracy"))
                .body("$",hasKey("name"))
                .body("$",hasKey("phone_number"))
                .body("$",hasKey("address"))
                .body("$",hasKey("types"))
                .body("$",hasKey("website"))
                .body("$",hasKey("language"));
    }
}
