package g15.token;

import g15.token.messages.TokensResponseMessage;
import messaging.Event;
import org.mockito.ArgumentMatcher;

public class ValidTokensResponseEventMatcher implements ArgumentMatcher<Event> {
    private final TokensResponseMessage left;
    public ValidTokensResponseEventMatcher(TokensResponseMessage response){
        left = response;
    }

    @Override
    public boolean matches(Event right) {
        var response = right.getArgument(0, TokensResponseMessage.class);
        return left.isSuccess() == response.isSuccess();
    }
}
