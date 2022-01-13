package g15.reporting;

import g15.reporting.adaptors.MessageAdaptor;
import g15.reporting.repositories.ReportRepository;
import g15.reporting.services.ReportService;
import messaging.implementations.RabbitMqQueue;

public class StartUp {
    public static void main(String[] args) throws Exception {
        new StartUp().startUp();
    }

    private void startUp() throws Exception {
        System.out.println("startup");

        var queue = new RabbitMqQueue("rabbitMq");
        ReportRepository reportRepository = new ReportRepository();
        ReportService service = new ReportService(reportRepository);

        new MessageAdaptor(queue, service);
        System.out.println("reporting running");
    }
}
