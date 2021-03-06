package de.demiway.bonus.backend.bff.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("health")
@Produces(MediaType.TEXT_PLAIN)
public class HealthResource {
    @GET
    public Response get() {
        return Response.ok().build();
    }
}
