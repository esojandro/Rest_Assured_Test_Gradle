import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.path.xml.XmlPath.from;

public class Test4 { // WIP

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
    }

    @Test
    public void createUserTest() {
        String requestBody = given()// responseBody
                .when()
                .body("{\n" +
                        "\"name\": \"morpheus\",\n" +
                        "\"job\": \"leader\"\n" +
                        "}")
                .post("/users")
                .then().extract().body().asString();

        User user = from(requestBody).getObject("", User.class);
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getJob());
        System.out.println(user.getCreatedAt());
    }
}
