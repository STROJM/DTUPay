package implementation;

/**
 * @author Roar Nind Steffensen
 */
public class MessagingClientFactory {
    private static MessagingClient client;
    public static MessagingClient create(String host){
        if(client == null) client = new RabbitMqClient(host);
        return client;
    }
    public static MessagingClient create(){
        return create("rabbitMq");
    }
    public static MessagingClient createAwaitableClient(){
        var client = create("rabbitMq");
        client.enableAwaitingCalls();
        return client;
    }
}
