package ch.zli.m223.controller;

import ch.zli.m223.model.Review;
import ch.zli.m223.service.ReviewService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;

@Path("/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewController {

    @Inject
    ReviewService reviewService;

    @GET
    @Operation(summary = "Get all reviews", description = "Get all reviews of de coworking space, everyone has access to it")
    public Response getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();

        if (!reviews.isEmpty()) {
            return Response.status(Response.Status.OK).entity(reviews).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @RolesAllowed({ "admin", "member" })
    @Operation(summary = "Create a Review", description = "Endpoint to create a review of the coworking space")
    public Response createReview(Review newReview) {
        Review createdReview = reviewService.createReview(newReview);
        if (createdReview != null) {
            return Response.status(Response.Status.CREATED).entity(createdReview).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @RolesAllowed("admin")
    @Path("/{reviewId}")
    @Operation(summary = "Delete a review", description = "Endpoint to delete a review of the coworking space, only admins have access to it")
    public Response deleteReview(@PathParam("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
