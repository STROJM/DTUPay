package g15.customerapi.Mapper;

import g15.customerapi.Models.AccountModel;
import messages.register.CustomerRegisterMessage;

public class CustomerMapper {
    public static CustomerRegisterMessage map(AccountModel accountModel){
        return new CustomerRegisterMessage(accountModel.getBankAccount());
    }
}
