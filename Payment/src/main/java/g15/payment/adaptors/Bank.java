package g15.payment.adaptors;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import g15.payment.exceptions.BankException;
import g15.payment.messages.EnrichedPayment;

public class Bank {
    private final BankService bankService = new BankServiceService().getBankServicePort();
    public void performPayment(EnrichedPayment payment) throws BankException {
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
}
