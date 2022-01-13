package g15.customerapi.Mapper;

import g15.customerapi.Models.CustomerModel;
import g15.customerapi.Service.messages.CustomerRegisterMessage;

public class CustomerMapper {
    public static CustomerRegisterMessage map(CustomerModel customerModel){
        return new CustomerRegisterMessage(customerModel.getCustomerBankAccount());
    }
}
