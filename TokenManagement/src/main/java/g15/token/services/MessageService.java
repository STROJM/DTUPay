package g15.token.services;

import g15.token.exceptions.InvalidTokenException;
import g15.token.exceptions.InvalidTokenRequestException;
import g15.token.messages.EnrichedPaymentMessage;
import g15.token.messages.PaymentMessage;
import g15.token.messages.TokensRequestMessage;
import g15.token.messages.TokensResponseMessage;
import messaging.v2.IMessagingClient;
import messaging.v2.Message;

public class MessageService {
    private final IMessagingClient messagingClient;
    private final TokenService tokenService = new TokenService();

    public MessageService(IMessagingClient messagingClient){
        this.messagingClient = messagingClient;
        this.messagingClient.register(this::handleTokensRequest, TokensRequestMessage.class);
        this.messagingClient.register(this::handleNonValidatedPaymentRequest, PaymentMessage.class);
    }

    public void handleTokensRequest(Message<TokensRequestMessage> message) {
        var response = getTokensResponse(message.model);
        this.messagingClient.reply(message.update(response));
    }

    private TokensResponseMessage getTokensResponse(TokensRequestMessage request){
        try {
            var tokens = tokenService.requestTokens(request.getCustomerBankAccount(), request.getTokensAmount());
            return new TokensResponseMessage(true, null, tokens);
        } catch (InvalidTokenRequestException e){
            return new TokensResponseMessage(false, e.getMessage(), null);
        }
    }

    public void handleNonValidatedPaymentRequest(Message<PaymentMessage> message) {
        EnrichedPaymentMessage enrichedRequest = getEnrichedPaymentMessage(message.model);
        this.messagingClient.forward(message.update(enrichedRequest), EnrichedPaymentMessage.class);
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
