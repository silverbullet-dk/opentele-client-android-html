Feature: Questionnaires

  Scenario: As a valid user I can see a list of questionnaires
    Given I am logged in as NancyAnn with password abcd1234
    Then I can navigate to the gennemfør målinger page
    And I should see a list of questionnaires

  Scenario: As a valid user I can fill out a blodsukker questionnaire
    Given I am logged in as NancyAnn with password abcd1234
    Then I can navigate to the gennemfør målinger page
    And I can navigate to the Blodsukker questionnaire
    Then I can fill out the Blodsukker questionnaire
    And send reply
    And go back to menu
