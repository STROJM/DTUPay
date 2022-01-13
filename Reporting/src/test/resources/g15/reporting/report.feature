Feature: Report testing

  Scenario: Successful payment reported
    Given a valid "EnrichedPaymentMessage" event for a payment of 100 kr is received
    When the payment amount is transferred in the bank
    Then the transaction has been stored
    And a valid "PaymentFinishedMessage" event is sent


  Scenario: Successful refund reported
    Given a valid "EnrichedRefundMessage" event for a refund of 100 kr is received
    When the refund amount is transferred in the bank
    Then the transaction has been stored
    And a valid "RefundFinishedMessage" event is sent