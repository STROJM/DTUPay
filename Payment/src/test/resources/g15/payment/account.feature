Feature: Account testing

  Scenario: Account registered
    Given a customer with bank account number "123"
    When the user registers in dtu pay
    Then the user successfully registers