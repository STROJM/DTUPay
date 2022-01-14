package g15.merchantapi.Service;

import g15.merchantapi.Service.messages.MerchantReportMessage;
import g15.merchantapi.Service.messages.MerchantReportResponse;
import messaging.v2.IMessagingClient;
import messaging.v2.MessagingClientFactory;

import javax.inject.Singleton;

@Singleton
public class ReportingService {
    private final IMessagingClient client;

    public ReportingService() {
        this.client = MessagingClientFactory.create();
    }

    public MerchantReportResponse getPreviousPayments(MerchantReportMessage request) {
        try {
            return client.call(request, MerchantReportResponse.class);
        } catch (Exception e) {
            return new MerchantReportResponse();
        }
    }
}
