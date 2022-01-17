Feature: Reporting

  Scenario: Successful transaction customer report
    Given a completed transaction event for a payment of 100 kr is received
    Then the customer who performed the transaction can get the report

  Scenario: Failed transaction customer report
    Given a failed transaction event for a payment of 100 kr is received
    Then the customer who performed the transaction can get the report

  Scenario: Successful transaction merchant report
    Given a completed transaction event for a payment of 100 kr is received
    Then the merchant who received the transaction can get the report

  Scenario: Failed transaction merchant report
    Given a failed transaction event for a payment of 100 kr is received
    Then the merchant who received the transaction can get the report

  Scenario: Successful transaction manager report
    Given a completed transaction event for a payment of 100 kr is received
    Then the manager can get the report

  Scenario: Failed transaction manager report
    Given a failed transaction event for a payment of 100 kr is received
    Then the manager can get the report