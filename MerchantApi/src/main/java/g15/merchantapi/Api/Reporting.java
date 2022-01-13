package g15.merchantapi.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Consumes({"application/json"})
@Path("/reporting")
public class Reporting {

    public Reporting() {
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String todo() {
        return "todo";
    }
}
