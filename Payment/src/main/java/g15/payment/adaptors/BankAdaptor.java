package g15.payment.adaptors;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import g15.payment.exceptions.BankException;
import g15.payment.messages.EnrichedPaymentMessage;
import g15.payment.messages.EnrichedRefundMessage;

public class BankAdaptor {
    private final BankService bankService = new BankServiceService().getBankServicePort();
    public void performPayment(EnrichedPaymentMessage payment) throws BankException {
        try {
            bankService.transferMoneyFromTo(
                    payment.getCustomerBankAccount(),
                    payment.getMerchantBankAccount(),
                    payment.getAmount(),
                    payment.getDescription()
            );
        } catch (BankServiceException_Exception e) {
            throw new BankException(e.getMessage());
        }
    }

    public void performRefund(EnrichedRefundMessage refund) throws BankException {
        try {
            bankService.transferMoneyFromTo(
                    refund.getCustomerBankAccount(),
                    refund.getMerchantBankAccount(),
                    refund.getAmount(),
                    refund.getDescription()
            );
        } catch (BankServiceException_Exception e) {
            throw new BankException(e.getMessage());
        }
    }
}
