package g15.customerapi.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes({"application/json"})
@Path("/payments")
public class Payments {

    public Payments() {
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String todo() {
        return "todo";
    }
}
