Feature: View my profile in the application
  As a logged in user
  I want to view my profile
  So that I can see my inserted data

  Scenario: View profile with existing phone number
    Given I am logged in
    And I am on the Main Screen
    When I click on the View Profile view
    Then will be forwarded to the View Profile Screen
    And I will see all my data

  Scenario: View profile without existing phone number
    Given I am logged in
    And I am on the Main Screen
    When I click on the View Profile view
    Then will be forwarded to the View Profile Screen
    And I will see all my data
    And I will see a message that I should submit my phone number
