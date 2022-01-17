package g15.managerapi.Service;


import implementation.IMessagingClient;
import implementation.MessagingClientFactory;
import messages.reporting.ManagerReportMessage;
import messages.reporting.ManagerReportResponse;

import javax.inject.Singleton;

@Singleton
public class ReportingService {
    private final IMessagingClient client;

    public ReportingService() {
        this.client = MessagingClientFactory.createAwaitableClient();
    }

    public ManagerReportResponse getManagerReport(ManagerReportMessage request){
        try {
            return client.call(request, ManagerReportResponse.class);
        } catch (Exception e) {
            return new ManagerReportResponse();
        }
    }
}
