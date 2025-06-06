Feature: Login Functionality

  Scenario: Login with valid credentials
    Given User navigates to the login page
    When User enters valid email and password
    Then User should be logged in successfully