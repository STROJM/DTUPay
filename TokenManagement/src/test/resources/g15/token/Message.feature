Feature: Message testing

  Scenario: Token generation request message
    Given a customer with bank account number "bankaccountnr"
    When the "TokensRequest" event for 5 tokens is received
    Then the "TokensResponse" valid event for 5 tokens is sent

#
#  Scenario: Enriching payment request message
#    Given a customer with bank account number "bankaccountnr"
#    When the "PaymentRequest" event for a payment request is received
#    Then the "EnrichedPaymentRequest" valid event for an enriched payment request is sent