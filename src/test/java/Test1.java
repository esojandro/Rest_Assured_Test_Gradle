import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

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

    @Test
    public void loginTestII(){

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "\"email\": \"eve.holt@reqres.in\",\n" +
                        "\"password\": \"cityslicka\"\n" +
                        "}")
                .post("https://reqres.in/api/login")
                .then()
                .log().all() //Para ver el token
                .statusCode(200);
    }

    @Test
    public void loginTestIII(){

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "\"email\": \"eve.holt@reqres.in\",\n" +
                        "\"password\": \"cityslicka\"\n" +
                        "}")
                .post("https://reqres.in/api/login")
                .then()
                .log().all() //Para ver el token
                .statusCode(200)
                .body("token", notNullValue()); // Comprueba que el token no sea nulo
    }
}
