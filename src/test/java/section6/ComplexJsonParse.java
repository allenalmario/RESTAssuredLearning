package section6;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class ComplexJsonParse {

    @Test
    public void complexJsonParse() {

        JsonPath jsonPath = new JsonPath(Payload.CoursePrice());

        // print number of courses

        int count = jsonPath.getInt("courses.size()");
        System.out.println(count);

        // print purchase amount
        int totalAmount = jsonPath.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);

        //print title of first course
        String titleFirstCourse = jsonPath.get("courses[0].title");
        System.out.println(titleFirstCourse);

        // print all course titles and their respective prices
        for (int i=0;i<count;i++) {
            String courseTitles = jsonPath.get("courses["+i+"].title");
            System.out.println(courseTitles);
            int coursePrices = jsonPath.get("courses["+i+"].price");
            System.out.println(coursePrices);
        }

        // print number of copies sold by RPA course
        System.out.println("Print number of copies sold by RPA course");
        for (int i=0;i<count;i++) {
            String courseTitles = jsonPath.get("courses["+i+"].title");
            if (courseTitles.equals("RPA")) {
                // copies sold
                System.out.println(jsonPath.get("courses["+i+"].copies").toString());
                break;
            }
        }
    }

}
