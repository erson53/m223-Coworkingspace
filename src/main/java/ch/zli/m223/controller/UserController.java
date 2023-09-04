package ch.zli.m223.controller;

import ch.zli.m223.model.AppUser;
import ch.zli.m223.service.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @GET
    public List<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GET
    @Path("/{userId}")
    public Response getUserById(@PathParam("userId") Long userId) {
        AppUser user = userService.getUserById(userId);
        if (user != null) {
            return Response.status(Response.Status.OK).entity(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response createUser(@Valid AppUser newUser) {
        AppUser createdUser = userService.createUser(newUser);
        if (createdUser != null) {
            return Response.status(Response.Status.CREATED).entity(createdUser).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{userId}")
    public Response updateUser(@PathParam("userId") Long userId, @Valid AppUser updatedUser) {
        AppUser user = userService.updateUser(userId, updatedUser);
        if (user != null) {
            return Response.status(Response.Status.OK).entity(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{userId}")
    public Response deleteUser(@PathParam("userId") Long userId) {
        boolean deleted = userService.deleteUser(userId);
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
