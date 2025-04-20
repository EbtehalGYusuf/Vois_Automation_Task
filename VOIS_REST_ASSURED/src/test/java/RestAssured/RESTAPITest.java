package RestAssured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.greaterThan;
import static org.testng.Assert.assertTrue;

public class RESTAPITest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test(enabled = false)
    public void testGetAllPosts() {
        given().when().get("/posts").then().statusCode(200).body("size()", greaterThan(0));
        String response = given().when().get("/posts").then().statusCode(200).extract().asString();
        String title = from(response).getString("[0].title");
        System.out.println(title);
    }

    @Test(enabled = true)
    public void testCase1() {
        given().when().get("/users").then().statusCode(200).body("size()", greaterThan(0));
        Response postResponse = RestAssured
                .given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .response();
        Response usersResponse = RestAssured
                .given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();
        int userCount = usersResponse.jsonPath().getList("$").size();
        int randomInt = (int) (Math.random() * userCount);
        int userId = postResponse.jsonPath().getInt("[" + randomInt + "].userId");   // getting random userId from posts page
        String email = usersResponse.jsonPath().getString("find { it.id == " + userId + " }.email"); //using random userid to get its email from users page
        String posts = postResponse.jsonPath().getString("[" + userId + "].body"); // getting user's post from posts page
        int postId = postResponse.jsonPath().getInt("[" + userId + "].id");  // getting posts id
        String title = "TestCaseTitle";
        String body = "TestCaseTitlewithBody";
        assertTrue(postId >= 1 && postId <= 100, "Post ID should be between 1 and 100"); //Verifying that  post id is within range from 1 to 100
        String jsonBody = """
                {
                    "userId": %d,
                    "id": %d,
                    "title": "%s",
                    "body": "%s"
                }
                """.formatted(userId, postId, title, body);
        Response postReqResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(jsonBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201) // 201 = Created
                .extract()
                .response();
        System.out.println("Random Post Index: " + randomInt);
        System.out.println("User ID from post: " + userId);
        System.out.println("User's Email: " + email);
        System.out.println("User's Post: " + posts);
        System.out.println("User's Post's id is valid and it is: " + postId);
        System.out.println("Post Response: " + postReqResponse);
    }
}
