# @author Mikkel Denker (s184193)
Feature: Payment testing

  Scenario: Test payment
    Given a customer and a merchant with a valid bank account number and balance 1000
    And the customer and the merchant is registered in DTUPay
    And the customer has a valid token
    When the merchant initiates a payment for the customer of 100 kr
    Then the payment is successful
    And the customer has balance 900
    And the merchant has balance 1100

  Scenario: Test refund
    Given a customer and a merchant with a valid bank account number and balance 1000
    And the customer and the merchant is registered in DTUPay
    And the customer has a valid token
    When the merchant initiates a refund for the customer of 100 kr
    Then the payment is successful
    And the customer has balance 1100
    And the merchant has balance 900
