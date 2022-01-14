package messaging.v2;

public class MessagingClientFactory {
    private static IMessagingClient client;
    public static IMessagingClient create(String host){
        if(client == null) client = new RabbitMqClient(host);
        return client;
    }
    public static IMessagingClient create(){
        return create("rabbitMq");
    }
}
