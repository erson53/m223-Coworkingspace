package ch.zli.m223;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;

import io.quarkus.test.h2.H2DatabaseTestResource;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@TestMethodOrder(OrderAnnotation.class)
@TestSecurity(user = "Admin@admin2.com", roles = "admin")
public class BookingControllerTest {

  @Test
  public void testGetAllBookingsEndpoint() {
    given()
        .when().get("/bookings")
        .then()
        .statusCode(200);
  }

  @Test
  public void testGetPendingBookingRequestsEndpoint() {
    given()
        .when().get("/bookings/booking-requests")
        .then()
        .statusCode(Response.Status.OK.getStatusCode());
  }
}
