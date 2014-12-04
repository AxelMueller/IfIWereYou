Feature: Registration and login of a user
  As a user
  I want to log in the application or register for it if neccessary
  So that I can use all functionalities of the application

  Scenario: Email login success
    Given I am on the Login Screen
	When I press the "Log in with E-Mail" button
	And I see "LOGIN"
    And I enter text "si.tenbeitel@gmx.de" into field with id "loginEmail"
    And I enter text "ifiwereyou" into field with id "loginPassword"
    And I press the "Login" button
    Then I wait for the "MainActivity" screen to appear
    

  Scenario: Email Registration failure (account already exists)
   Given I am on the Register Screen
    When I enter text "si.tenbeitel@gmx.de" into field with id "registerEmail"
    And I press the "Registrieren" button
    Then I wait up to 30 seconds for "Bitte Namen angeben um Registrierung abzuschlieﬂen" to appear
  

  Scenario: Email login failure
    Given I am on the Login Screen
	When I press the "Log in with E-Mail" button
	And I see "LOGIN"
    And I enter text "si.tenbeitel@gmx.de" into field with id "loginEmail"
    And I enter text "wrongpassword" into field with id "loginPassword"
    And I press the "Login" button
    Then I see "LOGIN"

	



