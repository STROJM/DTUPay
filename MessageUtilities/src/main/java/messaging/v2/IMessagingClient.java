package messaging.v2;

import java.io.IOException;
import java.util.function.Consumer;

public interface IMessagingClient {
    <TModel> void reply(Message<TModel> message);

    <TModel> void forward(Message<TModel> message, Class<TModel> modelType);

    <TModel> void register(Consumer<Message<TModel>> handler, Class<TModel> modelClass);

    void enableAwaitingCalls();
    <TRequest,TResponse> TResponse call(TRequest request, Class<TResponse> responseType) throws IOException, InterruptedException;
}
