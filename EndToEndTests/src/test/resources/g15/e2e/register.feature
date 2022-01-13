Feature: Register testing

  Scenario: Register user request test
    Given a customer with a valid bank account number
    When the customer register at DTUPay
    Then the register request is successful