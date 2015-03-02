Feature: Login

  Scenario: As a valid user I can log into the HTML5 client
    When I am logged in as NancyAnn with password abcd1234
    Then I should see the menu
