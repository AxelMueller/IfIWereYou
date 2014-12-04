Feature: Invite a friend for using the application
  As a logged in user
  I want to invite a friend 
  So that friend will receive an e-mail to register for the application

Scenario: contact to be invited is not registered for the application
	Given I am logged in
	And I am on the Invite Friend Screen
	And the contact to be invited is not registered for the application
	When I enter text "si.tenbeitel@gmx.de" into field with id "add_contact_emailEditText"
	And I go back
	And I press the "Hinzufügen" button
	Then I wait up to 30 seconds for "Es gibt keinen Benutzer mit der E-Mail Adresse si.tenbeitel@gmx" to appear
	And this contact will receive an e-mail ??
	
	Scenario: contact to be added is registered for the application
	Given I am logged in
	And I am on the add contact screen
	And the contact to be added is not registered for the application
	When I press view with id "action_add_contact"
	And I wait for the "AddContactActivity" screen to appear
	And I enter text "si.tenbeitel@gmx.de" into field with id "add_contact_emailEditText"
	And I go back
	And I press the "Hinzufügen" button
	Then I wait up to 30 seconds for "Es gibt keinen Benutzer mit der E-Mail Adresse si.tenbeitel@gmx" to appear

	



