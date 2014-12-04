Feature: Registration and login of a user
  As a user
  I want to log in the application or register for it if neccessary
  So that I can use all functionalities of the application

  Scenario: Email login success
    Given I have installed and opened the ‘If I Were You App’ on Android
    And I have a stable internet connection
    And I have an existing account
    When I select ‘Login with Email’
    And I enter the e-mail address linked to my account
    And I enter my correct password
    Then I will be logged in the application
    And the main screen will be displayed

  Scenario: Facebook login with data access granted and existing account
    Given I have installed and opened the ‘If I Were You App’ on Android
    And I have a stable internet connection
    And I have a Facebook account
    And I have granted access rights to my Facebook data
    And I have an account linked to my Facebook e-mail address
    When I select ‘Log in with Facebook’
    Then I will be logged in the application
    And the main screen will be displayed

  Scenario: Email Registration
    Given I have installed and opened the ‘If I Were You App’ on Android
    And I have a stable internet connection
    And there is no existing account linked to my email
    When I select ‘Login with Email’
    And I select ‘Register new account’
    And I enter my e-mail
    And I enter my name
    And I enter my password twice
    Then I will receive a validation e-mail that an account has been created for me
    And I will be logged in the application
    And the main screen will be displayed

  Scenario: Facebook registration with data access granted 
    Given I have installed and opened the ‘If I Were You App’ on Android
    And I have a stable internet connection
    And I have a Facebook account
    And I have granted access rights to my Facebook data
    And I do not have an account linked to my Facebook e-mail address
    When I select ‘Log in with Facebook’
    Then I will receive an e-mail that a new account was created for me
    And I will be logged in the application
    And the main screen will be displayed

  Scenario: Facebook login with data access denied
    Given I have installed and opened the ‘If I Were You App’ on Android
    And I have a stable internet connection
    And I have a Facebook account
    And I have denied access rights to my Facebook data
    When I select ‘Log in with Facebook’
    Then the login screen will be displayed again with an error message

  Scenario: Email login failed
    Given I have installed and opened the ‘If I Were You App’ on Android
    And I have a stable internet connection
    And I have an existing account
    When I select ‘Login with Email’
    And I enter the e-mail address linked to my account
    And I enter a wrong password
    Then I will see a message that the inserted password is incorrect
    And the log in with Email screen will be displayed
