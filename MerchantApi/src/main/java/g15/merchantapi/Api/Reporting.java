package g15.merchantapi.Api;

import g15.merchantapi.Models.Response.TypedResponseModel;
import g15.merchantapi.Service.ReportingService;
import messages.reporting.MerchantReportMessage;
import messages.reporting.MerchantTransactionReport;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/reporting/{id}")
public class Reporting {

    ReportingService service;

    public Reporting(ReportingService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TypedResponseModel<List<MerchantTransactionReport>> getPreviousPayments(@PathParam("id") String merchantId) {
        var reportingResponseMessage = service.getPreviousPayments(new MerchantReportMessage(merchantId));

        var response = new TypedResponseModel<List<MerchantTransactionReport>>();

        response.model = reportingResponseMessage.getAllTransactions();
        response.completed = true;

        return response;
    }
}
