package section12;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import section11.pojo.AddPlace;
import section11.pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {

    @Test
    public void specBuilderTest() {

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

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        RequestSpecification res = given().spec(req).body(place);

                Response response = res.when().post("/maps/api/place/add/json")
                .then().spec(resspec).extract().response();

        String responseString = response.asString();
        System.out.println(responseString);

    }

}
