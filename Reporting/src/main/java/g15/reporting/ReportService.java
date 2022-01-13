package g15.reporting;

import g15.reporting.messages.Report;
import g15.reporting.repositories.ReportRepository;

public class ReportService {
    private ReportRepository reportRepository;


    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void saveReport(Report report) {
        reportRepository.storeReport(report);
    }
}
