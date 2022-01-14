package g15.managerapi.Api;

import g15.managerapi.Models.Response.TypedResponseModel;
import g15.managerapi.Service.ReportingService;
import g15.managerapi.Service.messages.ManagerReportMessage;
import g15.managerapi.Service.messages.ManagerReportResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/reporting")
public class Reporting {
    ReportingService service;

    public Reporting(ReportingService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TypedResponseModel<String> getManagerReport() {
        ManagerReportResponse reportResponseMessage = service.getManagerReport(new ManagerReportMessage());
        // NOTE: Error-handling removed, this should always succeed.
        var result = new TypedResponseModel<String>();
        result.completed = true;
        // TODO: Skal model vare rapporten her?????
        result.model = "";
        return result;
    }
}
