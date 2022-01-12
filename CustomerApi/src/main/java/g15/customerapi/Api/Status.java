package g15.customerapi.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/status")
public class Status {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String status() {
        return "Customer API is up!";
    }
}
