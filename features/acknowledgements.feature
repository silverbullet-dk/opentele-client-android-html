Feature: Acknowledgements

  Scenario: As a valid user I can see my acknowledgements, if I have any
    Given I am logged in as NancyAnn with password abcd1234
    Then I can navigate to the bekræftigelser page
    And I should see a list of acknowledgements
