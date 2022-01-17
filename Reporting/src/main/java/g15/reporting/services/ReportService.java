package g15.reporting.services;


import g15.reporting.repositories.ReportRepository;
import messages.reporting.Report;

import java.util.List;

public class ReportService {
    private ReportRepository reportRepository;


    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void saveReport(Report report) {
        reportRepository.storeReport(report);
    }

    public List<Report> getReports() { return this.reportRepository.getReports(); }
}
