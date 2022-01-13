Feature: Payment

  Scenario: Successful Payment
    Given a valid "EnrichedPaymentMessage" event for a payment of 100 kr is received
    When the payment amount is transferred in the bank
    Then the transaction has been stored
    And a valid "PaymentFinishedMessage" event is sent

  Scenario: Payment with negative amount
    Given a valid "EnrichedPaymentMessage" event for a payment of -100 kr is received
    Then the transaction has not been stored
    And an invalid payment response event is sent with message "Cannot transfer negative amounts"

  Scenario: Received invalid payment message
    Given an invalid "EnrichedPaymentMessage" payment event
    Then an invalid payment response event is sent with message "Invalid token"

  Scenario: Successful refund
    Given a valid "EnrichedRefundMessage" event for a refund of 100 kr is received
    When the refund amount is transferred in the bank
    Then the transaction has been stored
    And a valid "RefundFinishedMessage" event is sent

  Scenario: Refund with negative amount
    Given a valid "EnrichedRefundMessage" event for a refund of -100 kr is received
    Then the transaction has not been stored
    And an invalid payment response event is sent with message "Cannot transfer negative amounts"

  Scenario: Received invalid payment message
    Given an invalid "EnrichedRefundMessage" refund event
    Then an invalid payment response event is sent with message "Invalid token"

# Given a customer with a bank account with balance 1000
# And that the customer is registered with DTU Pay
# And that the customer has a valid token
# Given a merchant with a bank account with balance 2000
# And that the merchant is registered with DTU Pay
# When the merchant initiates a payment for 100 kr by the customer
# Then the payment is successful
# And the balance of the customer at the bank is 900 kr
# And the balance of the merchant at the bank is 2100 kr

#  Scenario: Registering customer
#    Given a customer with a bank account with balance 1000
#    And that the customer is registered with DTU Pay
#    Then the registration is successful

#  Scenario: List of payments
#    Given a customer with a bank account with balance 1000
#    And that the customer is registered with DTU Pay
#    Given a merchant with a bank account with balance 2000
#    And that the merchant is registered with DTU Pay
#    Given a successful payment of 10 kr from customer to merchant
#    When the manager asks for a list of payments
#    Then the list contains a payments where customer paid 10 kr to merchant
#
#
#  Scenario: Customer is not known
#    Given a customer with unknown id
#    And a merchant with some id
#    When the merchant initiates a payment for 10 kr by the customer
#    Then the payment is not successful
#    And an error message is returned saying "creditor with id unknownCID is unknown"
#
#  Scenario: Merchant is not known
#    Given a customer with some id
#    And a merchant with unknown id
#    When the merchant initiates a payment for 10 kr by the customer
#    Then the payment is not successful
#    And an error message is returned saying "debtor with id unknownMID is unknown"