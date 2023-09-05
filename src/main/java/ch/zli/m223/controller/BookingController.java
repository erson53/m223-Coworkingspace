package ch.zli.m223.controller;

import ch.zli.m223.model.AppUser;
import ch.zli.m223.model.Booking;
import ch.zli.m223.service.BookingService;
import ch.zli.m223.service.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingController {

    @Inject
    BookingService bookingService;

    @Inject
    UserService userService;

    @POST
    public Response createBooking(@Valid Booking newBooking) {
        AppUser user = userService.getUserById(newBooking.getUser().getUser_id());
        newBooking.setUser(user);
        newBooking.setStatus("pending");
        Booking createdBooking = bookingService.createBooking(newBooking);

        if (createdBooking != null) {
            return Response.status(Response.Status.CREATED).entity(createdBooking).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @DELETE
    @Path("/{bookingId}")
    public Response deleteBooking(@PathParam("bookingId") Long bookingId) {
        boolean deleted = bookingService.deleteBooking(bookingId);
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/booking-requests")
    public List<Booking> getPendingBookingRequests() {
        return bookingService.getBookingsByStatus("pending");
    }

    @PUT
    @Path("/booking-requests/{bookingId}")
    public Response updateBookingRequestStatus(
            @PathParam("bookingId") Long bookingId,
            @QueryParam("status") String newStatus) {

        Booking updatedBooking = bookingService.updateBookingStatus(bookingId, newStatus);

        if (updatedBooking != null) {
            return Response.status(Response.Status.OK).entity(updatedBooking).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
