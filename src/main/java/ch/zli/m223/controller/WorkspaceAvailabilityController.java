package ch.zli.m223.controller;

import ch.zli.m223.model.WorkspaceAvailability;
import ch.zli.m223.service.WorkspaceAvailabilityService;

import javax.inject.Inject;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/workspace-availability")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkspaceAvailabilityController {

    @Inject
    WorkspaceAvailabilityService availabilityService;

    @GET
    @Path("/{date}")
    public Response getAvailabilityByDate(@PathParam("date") String date) {
        WorkspaceAvailability availability = availabilityService.getAvailabilityByDate(date);

        if (availability != null) {
            return Response.status(Response.Status.OK).entity(availability).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    public Response getAllAvailabilities() {
        List<WorkspaceAvailability> availabilities = availabilityService.getAllAvailabilities();

        if (!availabilities.isEmpty()) {
            return Response.status(Response.Status.OK).entity(availabilities).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @RolesAllowed("admin")
    public Response createAvailability(WorkspaceAvailability newAvailability) {
        WorkspaceAvailability createdAvailability = availabilityService.createAvailability(newAvailability);
        if (createdAvailability != null) {
            return Response.status(Response.Status.CREATED).entity(createdAvailability).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{date}")
    @RolesAllowed("admin")
    public Response updateAvailabilityByDate(
            @PathParam("date") String date,
            WorkspaceAvailability updatedAvailability) {

        WorkspaceAvailability updated = availabilityService.updateAvailabilityByDate(date, updatedAvailability);

        if (updated != null) {
            return Response.status(Response.Status.OK).entity(updated).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
