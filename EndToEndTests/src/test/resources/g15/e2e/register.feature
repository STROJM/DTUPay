Feature: Register testing

  Scenario: Register customer request test
    Given a customer with a valid bank account number
    When the customer register at DTUPay
    Then the customer register request is successful

  Scenario: Register merchant request test
    Given a merchant with a valid bank account number
    When the merchant register at DTUPay
    Then the merchant register request is successful