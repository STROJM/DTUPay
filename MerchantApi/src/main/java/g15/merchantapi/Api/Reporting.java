package g15.merchantapi.Api;

import g15.merchantapi.Models.Response.TypedResponseModel;
import g15.merchantapi.Adaptors.ReportingAdaptor;
import messages.reporting.MerchantReportMessage;
import messages.reporting.MerchantTransactionReport;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 * @author Mikkel Denker (s184193)
 */
@Path("/reporting/{id}")
public class Reporting {

    ReportingAdaptor service;

    public Reporting(ReportingAdaptor service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TypedResponseModel<List<MerchantTransactionReport>> getPreviousPayments(@PathParam("id") String merchantId) {
        var reportingResponseMessage = service.getPreviousPayments(new MerchantReportMessage(merchantId));

        var response = new TypedResponseModel<List<MerchantTransactionReport>>();

        response.model = reportingResponseMessage.getTransactionReports();
        response.completed = true;

        return response;
    }
}
