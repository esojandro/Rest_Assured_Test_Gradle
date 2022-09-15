import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Test3 {

    @Before
    public void setup(){
       RestAssured.baseURI = "https://reqres.in";
       RestAssured.basePath = "/api";
       RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    public void getUserTest(){
        given()
                .contentType(ContentType.JSON)
                .get("users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2)); // Comprueba que el data.id es 2
    }
}
