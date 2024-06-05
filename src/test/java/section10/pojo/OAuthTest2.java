package section10.pojo;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuthTest2 {

    @Test
    public void oAuthTest() {

        String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};

        String response = given()
                .formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        System.out.println(response);

        JsonPath jsonPath = new JsonPath(response);

        String accessToken = jsonPath.getString("access_token");

        GetCourse getCourse = given()
                .queryParam("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);

        System.out.println(getCourse.getLinkedIn());
        System.out.println(getCourse.getInstructor());

        System.out.println(getCourse.getCourses().getApi().get(1).getCourseTitle());

        List<Api> apiCourses = getCourse.getCourses().getApi();
        List<WebAutomation> webAutomationsCourses = getCourse.getCourses().getWebAutomation();

        ArrayList<String> a = new ArrayList<String>();

        for(int i = 0; i < apiCourses.size(); i++) {
            if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("soapui webservices testing")) {
                System.out.println(apiCourses.get(i).getPrice());
            }
        }

        for(int i = 0; i < webAutomationsCourses.size(); i++) {
            a.add(webAutomationsCourses.get(i).getCourseTitle());
        }

        //convert array to an array list for easier comparison of two array lists
        List<String> expectedCourses = Arrays.asList(courseTitles);

        Assert.assertTrue(a.equals(expectedCourses));


    }

}
