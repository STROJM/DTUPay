package g15.reporting.services;

import messages.reporting.Transaction;
import g15.reporting.repositories.ReportRepository;
import messages.reporting.CustomerReportResponse;
import messages.reporting.ManagerReportResponse;
import messages.reporting.MerchantReportResponse;

/**
 * @author Roar Nind Steffensen
 * @author Johannes Hald s202784
 * @author Søren Andersen s182881
 */
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void save(Transaction transactionEvent) {
        this.reportRepository.save(transactionEvent.getCustomerBankAccount(), transactionEvent.getReport());
        this.reportRepository.save(transactionEvent.getMerchantBankAccount(), transactionEvent.getReport());
    }

    public CustomerReportResponse getByCustomerId(String customerBankAccount) {
        var reports = this.reportRepository.getByUserId(customerBankAccount);
        return CustomerReportResponse.from(reports);
    }

    public MerchantReportResponse getByMerchantId(String merchantBankAccount) {
        var reports = this.reportRepository.getByUserId(merchantBankAccount);
        return MerchantReportResponse.from(reports);
    }

    public ManagerReportResponse getAll() {
        var reports = this.reportRepository.getAll();
        return ManagerReportResponse.from(reports);
    }
}
