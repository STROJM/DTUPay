package g15.customerapi.Mapper;

import g15.customerapi.Models.CustomerModel;
import g15.customerapi.Models.TokenModel;
import g15.customerapi.messages.CustomerRegisterMessage;
import g15.customerapi.messages.TokensRequestMessage;

public class CustomerMapper {
    public static CustomerRegisterMessage map(CustomerModel customerModel){
        return new CustomerRegisterMessage(customerModel.getCustomerBankAccount());
    }
}
