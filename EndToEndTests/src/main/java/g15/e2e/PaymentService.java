package g15.e2e;

import g15.e2e.Response.TypedResponseModel;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class PaymentService {
    public TypedResponseModel<String> pay(PaymentModel paymentModel){
        Client client = ClientBuilder.newClient();
        WebTarget r = client.target("http://localhost:8081/");

        TypedResponseModel<String> response =  r.path("/payments/pay")
                .request()
                .post(Entity.entity(paymentModel, MediaType.APPLICATION_JSON))
                .readEntity(new GenericType<>(){});
        client.close();
        return response;
    }
}
