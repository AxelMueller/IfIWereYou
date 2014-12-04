Given (/^I am on the Register Screen$/) do
steps %{
	Given I am on the Login Screen
  	Then I press the "Log in with E-Mail" button
	Then I wait for the "EmailLoginActivity" screen to appear
	Then I go back
	Then I press button number 2
	Then I wait for the "RegisterActivity" screen to appear
	}
end	

Given (/^I am on the Email Login Screen$/) do
steps %{
	Given I am on the Login Screen
  	Then I press the "Log in with E-Mail" button
	Then I go back
	Then I see login
	}
end	

Given (/^I am logged in$/) do
steps %{
	Given I am on the Login Screen
  	Then I press the "Log in with E-Mail" button
	Then I wait for the "EmailLoginActivity" screen to appear
	And I enter text "si.tenbeitel@gmx.de" into field with id "loginEmail"
    And I enter text "ifiwereyou" into field with id "loginPassword"
    And I press the "Login" button
    Then I wait for the "MainActivity" screen to appear
	}
end	

When (/^I enter only the e-mail address$/) do
steps %{
		Then I enter text "si.tenbeitel@gmx.de" into field with id "registerEmail"
		Then I go back
	}
end	

Given (/^I am on the Login Screen$/) do
steps %{
	Then I wait for the "LoginScreenActivity" screen to appear
	}
end	

And (/^I am on the Add Contact screen$/) do
steps %{
	When I press view with id "action_add_contact"
	And I wait for the "AddContactActivity" screen to appear
	}
end	

And (/^the contact to be added is registered for the application$/) do
steps %{
	}
end	
