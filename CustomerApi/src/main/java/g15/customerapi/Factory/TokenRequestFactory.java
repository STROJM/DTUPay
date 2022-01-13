package g15.customerapi.Factory;

import g15.customerapi.Service.TokenService;
import messaging.v2.RabbitMqClient;

public class TokenRequestFactory {
    static TokenService service = null;

    public TokenService getService() {
        if (service != null) {
            return service;
        }

        var mq = new RabbitMqClient("rabbitMq");
        service = new TokenService(mq);
        return service;
    }
}
