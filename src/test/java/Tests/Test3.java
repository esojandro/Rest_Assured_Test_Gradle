package Tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.path.json.JsonPath.from;

public class Test3 {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
    }

    @Test
    public void getUserTest() {
        given()
                .get("/users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.id", equalTo(2)); // Comprueba que el data.id es 2
    }

    @Test
    public void deleteUserTest() {
        given()
                .delete("/users/2")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void pathUserTest() { // El path es para actualizar solo un elemento del Json
        String nameUpdated = given()
                .when()
                .body("{\n" +
                        "\"name\": \"morpheus\",\n" +
                        "\"job\": \"zion resident\"\n" +
                        "}")
                .patch("/users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getString("name");

        assertThat(nameUpdated, equalTo("morpheus"));
    }

    @Test
    public void putUserTest() {
        String jobUpdated = given()
                .when()
                .body("{\n" +
                        "\"name\": \"morpheus\",\n" +
                        "\"job\": \"zion resident\"\n" +
                        "}")
                .put("/users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getString("job");

        assertThat(jobUpdated, equalTo("zion resident"));
    }

    @Test
    public void getAllUserTest() {
        Response response = given()
                .get("users?page=2");

        Headers headers = response.getHeaders();
        int statusCode = response.getStatusCode();
        String body = response.getBody().asString();
        String contenType = response.getSessionId();

        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        System.out.println("body: " + body);
        System.out.println("content type: " + contenType);
        System.out.println("headers: " + headers);
        System.out.println("**************************");
        System.out.println("**************************");
        System.out.println(headers.get("Content-Typpe"));
        System.out.println(headers.get("Transfer-Encoding"));
    }

    @Test

    public void getAllUsersTest() {
        String requestBody = given()// responseBody
                .when()
                .get("users?page=2")
                .then()
                .extract()
                .body()
                .asString();

        // Para extraer elementos de una respuesta
        int page = from(requestBody).get("page");
        int totalPages = from(requestBody).get("total_pages");

        // Para extraer elementos de un array en la respuesta
        int idFirstUser = from(requestBody).get("data[0].id"); // data es un arreglo

        /*System.out.println("page: " + page);
        System.out.println("total_pages: " + totalPages);
        System.out.println("id first user: " + idFirstUser);*/

        // Filtrar los usuarios con ID mayor a 10
        List<Map> usersWithIdGreasterThan10 = from(requestBody).get("data.findAll { user -> user.id > 10 }");
        String email = usersWithIdGreasterThan10.get(0).get("email").toString(); // Trae el email de los usuarios con id mayor a 10

        List<Map> user = from(requestBody).get("data.findAll { user -> user.id > 10 && user.last_name == 'Howell'}");
        int id = Integer.valueOf(user.get(0).get("id").toString());

    }
}