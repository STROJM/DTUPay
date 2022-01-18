#@author Roar Nind Steffensen

Feature: Message testing

  Scenario: Token generation request message
    Given a customer with bank account number "BankAccountNr"
    When the event for 5 tokens is received
    Then the valid token response event is sent

  Scenario: Enriching payment request message
    Given a customer with bank account number "BankAccountNr"
    And the customer owns an unused token
    When the event for a payment request is received
    Then a valid event for an enriched payment request is sent

  Scenario: Enriching payment request message
    Given a customer with bank account number "BankAccountNr"
    When the event for a payment request is received
    Then an invalid payment request event is sent with error "token is empty"