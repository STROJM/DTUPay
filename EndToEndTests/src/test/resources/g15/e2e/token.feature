Feature: Token testing

  Scenario: Token request test
    When a new customer with a new bank account requests 5 tokens
    Then the token request is successful