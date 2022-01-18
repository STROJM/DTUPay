package g15.merchantapi.Mapper;

import g15.merchantapi.Models.AccountModel;
import messages.register.MerchantRegisterMessage;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 */
public class MerchantMapper {
    public static MerchantRegisterMessage map(AccountModel accountModel) {
        return new MerchantRegisterMessage(accountModel.getBankAccount());
    }
}
