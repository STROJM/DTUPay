package g15.merchantapi.Mapper;

import g15.merchantapi.Models.MerchantModel;
import g15.merchantapi.Service.messages.MerchantRegisterMessage;

public class MerchantMapper {
    public static MerchantRegisterMessage map(MerchantModel merchantModel){
        return new MerchantRegisterMessage(merchantModel.getCustomerBankAccount());
    }
}
