Feature: Login Feature
  As a registered user
  I want to log into the application
  So that I can access my dashboard

  Scenario: Successful login with valid credentials
    Given I m on the login page
    When I enter "admin" in the username field
    And I enter "nimda" in the password field
    And I click the "Login" button
    Then I should be redirected to the dashboard page
    And I should see "Welcome, admin" on the page

  Scenario: Login with invalid credentials
    Given I am on the login page
    When I enter "invalidUser" in the username field
    And I enter "wrongPassword" in the password field
    And I click the "Login" button
    Then I should see an error message "Invalid username or password"

    

