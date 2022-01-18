package g15.customerapi.Api;

import g15.customerapi.Models.Response.TypedResponseModel;
import g15.customerapi.Service.ReportingService;
import messages.reporting.CustomerReportMessage;
import messages.reporting.CustomerTransactionReport;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 */
@Path("/reporting/{id}")
public class Reporting {
    ReportingService service;

    public Reporting(ReportingService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TypedResponseModel<List<CustomerTransactionReport>> getPreviousPayments(@PathParam("id") String customerId) {
        var reportingResponseMessage = service.getPreviousPayments(new CustomerReportMessage(customerId));

        var response = new TypedResponseModel<List<CustomerTransactionReport>>();

        response.model = reportingResponseMessage.getTransactionReports();
        response.completed = true;

        return response;
    }
}
