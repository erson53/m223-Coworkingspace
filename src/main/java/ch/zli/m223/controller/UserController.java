package ch.zli.m223.controller;

import ch.zli.m223.model.AppUser;
import ch.zli.m223.service.UserService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @GET
    @RolesAllowed("admin")
    @Operation(summary = "Get all users", description = "Returns a list of all users, only admins have access to it")
    public List<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GET
    @RolesAllowed("admin")
    @Path("/{userId}")
    @Operation(summary = "Get one user", description = "Returns a single user, only admins have access to it")

    public Response getUserById(@PathParam("userId") Long userId) {
        AppUser user = userService.getUserById(userId);
        if (user != null) {
            return Response.status(Response.Status.OK).entity(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @RolesAllowed("admin")
    @Operation(summary = "Create a user", description = "Creates a users, only admins have access to it")

    public Response createUser(@Valid AppUser newUser) {
        AppUser createdUser = userService.createUser(newUser);
        if (createdUser != null) {
            return Response.status(Response.Status.CREATED).entity(createdUser).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @RolesAllowed("admin")
    @Path("/{userId}")
    @Operation(summary = "Edit a user", description = "Edits a  user, only admins have access to it")

    public Response updateUser(@PathParam("userId") Long userId, @Valid AppUser updatedUser) {
        AppUser user = userService.updateUser(userId, updatedUser);
        if (user != null) {
            return Response.status(Response.Status.OK).entity(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @RolesAllowed("admin")
    @Path("/{userId}")
    @Operation(summary = "Delete a user", description = "Delets a user, only admins have access")

    public Response deleteUser(@PathParam("userId") Long userId) {
        boolean deleted = userService.deleteUser(userId);
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("/register")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    @Operation(summary = "Register", description = "Registers a new user")
    public AppUser create(AppUser user) {
        return userService.createUser(user);
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    @Operation(summary = "Login", description = "Longin of an existing user")
    public String login(AppUser user) {
        return userService.loginAppUser(user.getEmail(), user.getPassword());
    }
}
