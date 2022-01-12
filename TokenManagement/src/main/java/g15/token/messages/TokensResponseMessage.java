package g15.token.messages;

import java.io.Serializable;

public class TokensResponseMessage implements Serializable {
    private static final long serialVersionUID = 9023222984284806610L;

    private final boolean success;
    private final String errorMessage;
    private final String[] tokens;

    public TokensResponseMessage(boolean success, String errorMessage, String[] tokens) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.tokens = tokens;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String[] getTokens() {
        return tokens;
    }
}
