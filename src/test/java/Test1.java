import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

public class Test1 {
    @Test
    public void loginTest(){

        String requestBody = RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "\"email\": \"eve.holt@reqres.in\",\n" +
                        "\"password\": \"cityslicka\"\n" +
                        "}")
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .asString();

        System.out.println(requestBody);
    }
}
