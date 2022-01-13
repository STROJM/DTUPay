Feature: Reporting

  Scenario: Successful payment reported
    Given a valid "PaymentReportStoreMessage" event for a payment of 100 kr is received
    Then the report is stored
    And a valid "PaymentReportFinished" event is sent

  Scenario: Successful refund reported
    Given a valid "RefundReportStoreMessage" event for a refund of 100 kr is received
    Then the report is stored
    And a valid "RefundReportFinished" event is sent

  Scenario: Unsuccessful payment reported
    Given a valid "PaymentReportStoreMessage" event for a failed payment of 100 kr is received
    Then the report is stored
    And a valid "PaymentReportFinished" event is sent

  Scenario: Unsuccessful refund reported
    Given a valid "RefundReportStoreMessage" event for a failed refund of 100 kr is received
    Then the report is stored
    And a valid "RefundReportFinished" event is sent