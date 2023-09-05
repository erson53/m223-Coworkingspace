package ch.zli.m223;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.given;
import javax.ws.rs.core.MediaType;
import io.quarkus.test.h2.H2DatabaseTestResource;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@TestMethodOrder(OrderAnnotation.class)
@TestSecurity(user = "Admin@admin2.com", roles = "admin")
public class UserControllerTest {

        @Test
        public void testGetAllUsersEndpoint() {
                given()
                                .when().get("/users")
                                .then()
                                .statusCode(200);
        }

        @Test
        @Order(1)
        void testCreateUser() {
                given()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("{"
                                                + "\"name\": \"admin\","
                                                + "\"email\": \"admin@gmail.com\","
                                                + "\"password\": \"1234\","
                                                + "\"isAdmin\": true"
                                                + "}")
                                .when()
                                .post("/users")
                                .then()
                                .statusCode(201)
                                .extract()
                                .response();
        }

        @Test
        @Order(1)
        void testRegisterUser() {
                given()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("{"
                                                + "\"name\": \"admin2\","
                                                + "\"email\": \"admin2@gmail.com\","
                                                + "\"password\": \"1234\","
                                                + "\"isAdmin\": true"
                                                + "}")
                                .when()
                                .post("/users/register")
                                .then()
                                .statusCode(200)
                                .extract()
                                .response();
        }
}
