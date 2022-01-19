Feature: Token testing

  Scenario: Token request succeeds for registered user
    Given a registered customer
    When the customer requests 5 tokens
    Then the token request is successful

  Scenario: Token request fails for non-registered user
    Given a customer that is not registered
    When the customer requests 5 tokens
    Then the token request fails
