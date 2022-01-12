package g15.token.services;

import g15.token.exceptions.InvalidTokenException;
import g15.token.exceptions.InvalidTokenRequestException;
import g15.token.messages.EnrichedPaymentMessage;
import g15.token.messages.PaymentMessage;
import g15.token.messages.TokensRequestMessage;
import g15.token.messages.TokensResponseMessage;
import messaging.Event;
import messaging.MessageQueue;

public class MessageService {
    private final MessageQueue queue;
    private final TokenService tokenService = new TokenService();

    public MessageService(MessageQueue q){
        this.queue = q;
        this.queue.addHandler(
                "PaymentRequest",
                this::handleNonValidatedPaymentRequest
        );
        this.queue.addHandler(
                "TokensRequest",
                this::handleTokensRequest);
    }

    public void handleTokensRequest(Event event) {
        var request = event.getArgument(0, TokensRequestMessage.class);
        var response = getTokensResponse(request);
        queue.publish(new Event("TokensResponse", new Object[] {response}));
    }
    private TokensResponseMessage getTokensResponse(TokensRequestMessage request){
        try {
            var tokens = tokenService.requestTokens(request.getCustomerBankAccount(), request.getTokensAmount());
            return new TokensResponseMessage(true, null, tokens);
        } catch (InvalidTokenRequestException e){
            return new TokensResponseMessage(false, e.getMessage(), null);
        }
    }


    public void handleNonValidatedPaymentRequest(Event ev) {
        var request = ev.getArgument(0, PaymentMessage.class);
        EnrichedPaymentMessage enrichedRequest = getEnrichedPaymentMessage(request);
        queue.publish(new Event("EnrichedPaymentRequest", new Object[] { enrichedRequest }));
    }
    private EnrichedPaymentMessage getEnrichedPaymentMessage(PaymentMessage request) {
        try {
            var customerBankAccount = tokenService.useToken(request.getToken());
            return getEnrichedPaymentMessage(request, customerBankAccount, null);
        } catch (InvalidTokenException e){
            return getEnrichedPaymentMessage(request, null, e.getMessage());
        }
    }
    private EnrichedPaymentMessage getEnrichedPaymentMessage(
            PaymentMessage request,
            String customerBankAccount,
            String errorMessage
    ) {
        return new EnrichedPaymentMessage(
                customerBankAccount,
                request.getMerchantBankAccount(),
                request.getToken(),
                request.getAmount(),
                request.getDescription(),
                customerBankAccount != null,
                errorMessage
        );
    }
}
