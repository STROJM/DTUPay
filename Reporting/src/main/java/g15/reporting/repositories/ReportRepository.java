package g15.reporting.repositories;

import messages.reporting.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportRepository {
    private ArrayList<Report> reports = new ArrayList();

    public final void storeReport(Report report) {
        reports.add(report);
    }

    public final List<Report> getReports() {
        return reports;
    }
}
