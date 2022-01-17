package g15.merchantapi.Mapper;

import g15.merchantapi.Models.AccountModel;
import messages.register.MerchantRegisterMessage;

public class MerchantMapper {
    public static MerchantRegisterMessage map(AccountModel accountModel) {
        return new MerchantRegisterMessage(accountModel.getBankAccount());
    }
}
