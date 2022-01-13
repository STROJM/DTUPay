Feature: Payment testing

  Scenario: Test payment
    Given a customer and a merchant with a valid bank account number
    And the customer and the merchant is registered in DTUPay
    And the customer has a valid token
    When the merchant initiates a payment for the customer of 100 kr
    Then the payment is successful

  Scenario: Test refund
    Given a customer and a merchant with a valid bank account number
    And the customer and the merchant is registered in DTUPay
    And the customer has a valid token
    When the merchant initiates a refund for the customer of 100 kr
    Then the payment is successful
