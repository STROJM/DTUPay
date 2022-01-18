#@author Roar Nind Steffensen

Feature: Token usage testing

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