package g15.managerapi.Service;

import g15.managerapi.Service.messages.ManagerReportMessage;
import g15.managerapi.Service.messages.ManagerReportResponse;
import messaging.v2.IMessagingClient;
import messaging.v2.MessagingClientFactory;

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
