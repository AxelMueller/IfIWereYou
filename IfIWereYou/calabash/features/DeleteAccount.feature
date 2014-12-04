Feature: Delete account
  As a logged in user
  I delete my account
  So I will not be registered for the application anymore afterwards

  Scenario: Confirm Delete
    Given I am logged in
    And I am on the Main Screen
    When I select delete account from the menu 
    And I confirm the dialog
    Then I wait will be logged out
    And my user account will be deleted from the database

  Scenario: Cancel Delete
    Given I am logged in
    And I am on the Main Screen
    When I select delete account from the menu 
    And I cancel the dialog
    Then I will remain on the Main Screen and nothing will happen
