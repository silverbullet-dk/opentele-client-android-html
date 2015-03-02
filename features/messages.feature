Feature: Messages

  Scenario: As a valid user I can see my messages, if I have any
    Given I am logged in as NancyAnn with password abcd1234
    Then I can navigate to the beskeder page
    And I should see a list of threads
    Then I can navigate to the TCN thread
    And I should see a list of messages

  Scenario: As a valid user I can write a new message
    Given I am logged in as NancyAnn with password abcd1234
    Then I can navigate to the beskeder page
    And I should see a list of threads
    Then I can navigate to the TCN thread
    Then I can navigate to new message
    And I can write a new message with topic Test and message This is a test
