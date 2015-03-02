Feature: Change Password
	Scenario: As a user I can change my password
		Given I am logged in as NancyAnn with password abcd1234
		Then I can navigate to the skift adgangskode page
		And change my password to 1234abcd
		And submit my new password
		Then I should see the menu

	Scenario: As a user which has changed my password I can log in with the new password and change it again
		Given I am logged in as NancyAnn with password 1234abcd
		Then I can navigate to the skift adgangskode page
		And change my password to abcd1234
		And submit my new password
		Then I should see the menu
