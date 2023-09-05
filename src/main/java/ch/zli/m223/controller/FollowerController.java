package ch.zli.m223.controller;

import ch.zli.m223.model.AppUser;
import ch.zli.m223.model.Follower;
import ch.zli.m223.service.UserService;
import ch.zli.m223.service.FollowerService;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/followers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FollowerController {

    @Inject
    FollowerService followerService;

    @Inject
    UserService appUserService;

    @POST
    @Path("/{followerId}/follow/{followedId}")
    @RolesAllowed({ "admin", "member" })
    @Operation(summary = "Follow another user", description = "Endpoint to follow another user")
    public Response followUser(
            @PathParam("followerId") Long followerId,
            @PathParam("followedId") Long followedId) {

        AppUser followerUser = appUserService.getUserById(followerId);
        AppUser followedUser = appUserService.getUserById(followedId);

        if (followerUser != null && followedUser != null) {
            followerService.followUser(followerUser, followedUser);
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{followerId}/unfollow/{followedId}")
    @RolesAllowed({ "admin", "member" })
    @Operation(summary = "Unfollow another user", description = "Endpoint to unfollow another user")
    public Response unfollowUser(
            @PathParam("followerId") Long followerId,
            @PathParam("followedId") Long followedId) {

        AppUser followerUser = appUserService.getUserById(followerId);
        AppUser followedUser = appUserService.getUserById(followedId);

        if (followerUser != null && followedUser != null) {
            followerService.unfollowUser(followerUser, followedUser);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{userId}")
    @RolesAllowed({ "admin", "member" })
    @Operation(summary = "Get follwers of a user", description = "Returns a list of the followers from a user")
    public Response getFollowersForUser(@PathParam("userId") Long userId) {
        List<Follower> followers = followerService.getFollowersForUser(userId);

        if (!followers.isEmpty()) {
            return Response.status(Response.Status.OK).entity(followers).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
