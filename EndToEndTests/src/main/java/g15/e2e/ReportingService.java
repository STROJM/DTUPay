package g15.e2e;

import g15.e2e.Response.TypedResponseModel;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;

public class ReportingService {
    public TypedResponseModel<List<CustomerTransactionReport>> getCustomerPayments(String customerId){
        Client client = ClientBuilder.newClient();
        WebTarget r = client.target("http://localhost:8080/");

        TypedResponseModel<List<CustomerTransactionReport>> response = r.path("/reporting/" + customerId)
                .request()
                .get()
                .readEntity(new GenericType<>(){});

        client.close();
        return response;
    }

    public TypedResponseModel<List<MerchantTransactionReport>> getMerchantPayments(String merchantId){
        Client client = ClientBuilder.newClient();
        WebTarget r = client.target("http://localhost:8081/");

        TypedResponseModel<List<MerchantTransactionReport>> response = r.path("/reporting/" + merchantId)
                .request()
                .get()
                .readEntity(new GenericType<>(){});

        client.close();
        return response;
    }
}
