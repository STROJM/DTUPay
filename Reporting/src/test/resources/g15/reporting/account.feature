Feature: Account testing

  Scenario: Account registered
    Given a customer with bank account number "123"
    When the user registers in dtu pay
    Then the user successfully registers

  Scenario: Successful Payment
    Given a valid "EnrichedPaymentMessage" event for a payment of 100 kr is received
    When the payment amount is transferred in the bank
    Then the transaction has been stored
    And a valid "PaymentFinishedMessage" event is sent


  Scenario: Successful refund
    Given a valid "EnrichedRefundMessage" event for a refund of 100 kr is received
    When the refund amount is transferred in the bank
    Then the transaction has been stored
    And a valid "RefundFinishedMessage" event is sent