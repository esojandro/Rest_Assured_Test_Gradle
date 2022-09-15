import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

public class Test2 {

    @Test
    public void getUserTest(){

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all() //Para ver el token
                .statusCode(200)
                .body("data.id", equalTo(2)); // Comprueba que el data.id es 2
    }
}