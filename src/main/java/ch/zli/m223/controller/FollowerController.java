package ch.zli.m223.controller;

import ch.zli.m223.model.AppUser;
import ch.zli.m223.model.Follower;
import ch.zli.m223.service.UserService;
import ch.zli.m223.service.FollowerService;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response getFollowersForUser(@PathParam("userId") Long userId) {
        List<Follower> followers = followerService.getFollowersForUser(userId);

        if (!followers.isEmpty()) {
            return Response.status(Response.Status.OK).entity(followers).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
