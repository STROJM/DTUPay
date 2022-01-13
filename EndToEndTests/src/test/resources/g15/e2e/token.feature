Feature: Token testing

  Scenario: Token request test
    When a new customer with bank number "1234-1234-1234-1234" requests 5 tokens
    Then the request is successful