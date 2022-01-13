package g15.e2e;

import g15.e2e.Response.TypedResponseModel;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class AccountService {
    public TypedResponseModel<String> registerCustomer(CustomerModel request){
        Client client = ClientBuilder.newClient();
        WebTarget r = client.target("http://localhost:8080/");

        return r.path("/accounts")
                .request()
                .post(Entity.entity(request, MediaType.APPLICATION_JSON))
                .readEntity(new GenericType<>() {
                });
    }
}
