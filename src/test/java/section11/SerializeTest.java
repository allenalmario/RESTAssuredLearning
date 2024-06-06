package section11;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import section11.pojo.AddPlace;
import section11.pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializeTest {

    @Test
    public void serializeTest() {

        RestAssured.baseURI="https://rahulshettyacademy.com";

        AddPlace place = new AddPlace();

        place.setAccuracy(50);
        place.setAddress("42 Wallaby Way");
        place.setLanguage("English");
        place.setPhone_number("+1-123-45678");
        place.setWebsite("www.deezknee.com");
        place.setName("Nemo");
        List<String> myList = new ArrayList<String>();
        myList.add("Water Park");
        myList.add("Amusement Park");
        place.setTypes(myList);
        Location location = new Location();
        location.setLat(-12.412515);
        location.setLng(90.123045);
        place.setLocation(location);

        Response response = given().queryParam("key", "qaclick123").body(place)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();

        String responseString = response.asString();
        System.out.println(responseString);

    }

}
