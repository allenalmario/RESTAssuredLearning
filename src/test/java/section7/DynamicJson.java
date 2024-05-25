package section7;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class DynamicJson {

    @Test(dataProvider="BooksData")
    public void addBook(String isbn, String aisle) {

        RestAssured.baseURI="http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(Payload.Addbook(isbn, aisle))
                .when()
                .post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);

        String id = jsonPath.get("ID");

        System.out.println(id);

    }

    @DataProvider(name="BooksData")
    public Object[][] getData() {

        return new Object[][] {{"yzy","456"}, {"erg", "453"}, {"wer", "967"}};

    }

}
