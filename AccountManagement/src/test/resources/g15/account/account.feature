# /**
# * @author Johannes Hald s202784
# */

Feature: Account testing

  Scenario: Customer registers in dtu pay
    Given a customer with a valid bank account number "replace-with-valid-customer-bank-acc-number"
    When the user registers in dtu pay
    Then the user successfully registers

  Scenario: Merchant registers in dtu pay
    Given a merchant with a valid bank account number "replace-with-valid-merchant-bank-acc-number"
    When the user registers in dtu pay
    Then the user successfully registers

  Scenario: Customer registers in dtu pay with invalid account
    Given a customer with a invalid bank account number "invalid-customer-bank-acc-number"
    When the user registers in dtu pay
    Then the user fails to register

  Scenario: Merchant registers in dtu pay with invalid account
    Given a merchant with a invalid bank account number "invalid-merchant-bank-acc-number"
    When the user registers in dtu pay
    Then the user fails to register