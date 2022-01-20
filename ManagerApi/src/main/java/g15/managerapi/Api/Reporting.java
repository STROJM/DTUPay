package g15.managerapi.Api;

import g15.managerapi.Models.Response.TypedResponseModel;
import g15.managerapi.Adaptors.ReportingAdaptor;
import messages.reporting.ManagerReportMessage;
import messages.reporting.ManagerTransactionReport;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/reporting")
public class Reporting {
    ReportingAdaptor service;

    public Reporting(ReportingAdaptor service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TypedResponseModel<List<ManagerTransactionReport>> getPreviousPayments() {
        var reportingResponseMessage = service.getManagerReport(new ManagerReportMessage());

        var response = new TypedResponseModel<List<ManagerTransactionReport>>();

        response.model = reportingResponseMessage.getTransactionReports();
        response.completed = true;

        return response;
    }
}
