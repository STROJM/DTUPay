Feature: Token request testing

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


