package section6;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {

    @Test
    public void sumValidation() {

        // verify sum

        JsonPath jsonPath = new JsonPath(Payload.CoursePrice());
        int count = jsonPath.getInt("courses.size()");

        int sum = 0;

        for(int i=0;i<count;i++) {

            int price = jsonPath.get("courses["+i+"].price");
            int copies = jsonPath.get("courses["+i+"].copies");
            sum += price * copies;

        }

        System.out.println(jsonPath.getInt("dashboard.purchaseAmount"));
        System.out.println(sum);

        Assert.assertEquals(sum, jsonPath.getInt("dashboard.purchaseAmount"));

    }

}
