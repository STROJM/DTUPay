package messaging.v2;

import java.util.concurrent.BlockingQueue;

public class CallAwaiter<TResponse> {
    private final BlockingQueue<TResponse> response;
    private final Class<TResponse> responseType;

    public CallAwaiter(BlockingQueue<TResponse> response, Class<TResponse> responseType) {
        this.response = response;
        this.responseType = responseType;
    }

    public static <TResponse> CallAwaiter from(BlockingQueue<TResponse> response, Class<TResponse> responseType) {
        return new CallAwaiter(response, responseType);
    }

    public BlockingQueue<TResponse> getResponse() {
        return response;
    }

    public Class<TResponse> getResponseType() {
        return responseType;
    }
}
