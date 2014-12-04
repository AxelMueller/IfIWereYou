Feature: Add new contact to contact list
  As a logged in user
  I want to add a new contact 
  So that this contact will then be added to my contact list

Scenario: contact to be added is registered for the application
	Given I am logged in
	And I am on the Add Contact screen
	And the contact to be added is registered for the application
	When I enter text "si.tenbeitel@gmx.de" into field with id "add_contact_emailEditText"
	And I go back
	And I press the "Hinzufügen" button
	Then I wait up to 30 seconds for "Kontakt erfolgreich hinzugefügt" to appear
	And I will remain on the Add contact screen
	
	Scenario: contact to be added is registered for the application
	Given I am logged in
	And I am on the Add Contact screen
	And the contact to be added is not registered for the application
	When I enter text "test@testmail.de" into field with id "add_contact_emailEditText"
	And I go back
	And I press the "Hinzufügen" button
	Then I wait up to 30 seconds for "Es gibt keinen Benutzer mit der E-Mail Adresse si.tenbeitel@gmx" to appear

	



