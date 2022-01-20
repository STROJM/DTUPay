package implementation;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author Roar Nind Steffensen
 */
public interface MessagingClient {
    <TModel> void reply(Message<TModel> message);
    <TModel> void forward(Message<TModel> message, Class<TModel> modelType);
    <TModel> void send(TModel model);
    <TModel> void register(Consumer<Message<TModel>> handler, Class<TModel> modelClass);
    void enableAwaitingCalls();
    <TRequest,TResponse> TResponse call(TRequest request, Class<TResponse> responseType) throws IOException, InterruptedException;
}
