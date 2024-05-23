package section_4_5;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {

    // validate if add place api is working as expected
    // given - all input details
    // when - submit the api -resource,http method
    // then - validate the response
    @Test
    public void basics() {

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(Payload.addPlace()).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

        // add place -> Update place with new address -> get place to validate if new address is present in response

        JsonPath jsonPath = new JsonPath(response); // for parsing json

        String placeId = jsonPath.getString("place_id");

        System.out.println(placeId);

        // update place

        String newAddress = "70 Summer walk, USA";

        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

        // get place to verify update worked

       String getPlaceResponse =  given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

       JsonPath jp = new JsonPath(getPlaceResponse);
       String actualAddress = jp.getString("address");

        System.out.println(actualAddress);

        Assert.assertEquals(actualAddress, newAddress);

    }

}
