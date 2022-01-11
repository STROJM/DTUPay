Feature: Token testing

  Scenario: Customer requests new tokens
    Given a customer with id "cid1"
    When the customer requests 5 tokens
    Then the 5 tokens are generated

  Scenario: Customer with 2 tokens requests new tokens
    Given a customer with id "cid1"
    And the customer has 2 tokens
    When the customer requests 5 tokens
    Then the request is denied with message "the customer already has 2 tokens"

  Scenario: Customer with 1 token requests new tokens
    Given a customer with id "cid1"
    And the customer has 1 tokens
    When the customer requests 5 tokens
    Then the 5 tokens are generated

  Scenario: Customer with 1 token requests new tokens
    Given a customer with id "cid1"
    When the customer requests 6 tokens
    Then the request is denied with message "invalid amount of requested tokens"

  Scenario: Customer with 1 token requests new tokens
    Given a customer with id "cid1"
    When the customer requests 0 tokens
    Then the request is denied with message "invalid amount of requested tokens"

  #token is non empty
  #token is unused
  #token has a customer
  Scenario: Customer lookup with empty token
    Given an empty token
    When token is used
    Then the lookup fails with message "token is empty"

  Scenario: Customer lookup with invalid token
    Given token "fake_token123"
    When token is used
    Then the lookup fails with message "token is invalid"

  Scenario: Customer lookup with token
    Given a token for customerId "cid1"
    When token is used
    Then the lookup has succeeded
    And the customer id "cid1" is returned
    When token is used
    Then the lookup fails with message "token is invalid"

