Feature: Reporting

  Scenario: Registered customer requests list of payments
    Given a merchant and customer with a valid bank account number
    And the merchant and customer is registered in DTUPay
    And the customer has a previous payment to the merchant of 100 kr
    When the customer requests a list of previous payments
    Then the list contains a payment to the merchant from the customer for 100 kr

  Scenario: Registered customer requests list of payments without payments
    Given a merchant and customer with a valid bank account number
    And the merchant and customer is registered in DTUPay
    When the customer requests a list of previous payments
    Then the customer report list is empty

  Scenario: Customer cannot see invalid payments
    Given a merchant and customer with a valid bank account number
    And the merchant and customer is registered in DTUPay
    And the customer has a previous payment to the merchant of -100 kr
    When the customer requests a list of previous payments
    Then the customer report list is empty

  Scenario: Registered merchant requests list of payments
    Given a merchant and customer with a valid bank account number
    And the merchant and customer is registered in DTUPay
    And the customer has a previous payment to the merchant of 100 kr
    When the merchant requests a list of previous payments
    Then the merchant list contains a payment to the merchant for 100 kr

  Scenario: Registered merchant requests list of payments without payments
    Given a merchant and customer with a valid bank account number
    And the merchant and customer is registered in DTUPay
    When the merchant requests a list of previous payments
    Then the merchant report list is empty

  Scenario: Merchant cannot see invalid payments
    Given a merchant and customer with a valid bank account number
    And the merchant and customer is registered in DTUPay
    And the customer has a previous payment to the merchant of -100 kr
    When the merchant requests a list of previous payments
    Then the merchant report list is empty
