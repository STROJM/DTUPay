package g15.customerapi.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/accounts")
public class Accounts {

    public Accounts() {

    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String todo() {
        return "todo";
    }
}
