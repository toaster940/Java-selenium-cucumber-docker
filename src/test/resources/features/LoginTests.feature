Feature: Login tests
  As Customer, I can login to Softwaretestingboard.com

  Background: Home page of Softwaretestingboard.com
    Given User navigates to Login page

  @DataTableHeaders
  Scenario: Login to Bhinneka.com using valid account that registered in personal and corporate account type
    Given Login page is displayed
    When Input credentials to login with headers table
    | Email             | Password     | Account Type           |
    | your@email.com    | Yourp@ssw0rd | personal and corporate |
    And Click selanjutnya button

  @SimplyDataTable
  Scenario: Login to Softwaretestingboard.com using valid account that registered in personal account type
    Given Login page is displayed
    When Input credentials to login without headers
      | your@email.com | Yourp@ssw0rd | personal |
    And Click selanjutnya button

  @ValidLogin
  Scenario: User signs in to Softwaretestingboard.com using valid credentials
    Given Login page is displayed
    When User inputs email "depkedu2others@gmail.com" and password "Password@1"
    And User clicks sign in button
    Then User lands on landing page