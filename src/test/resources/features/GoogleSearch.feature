Feature: Google Search Functionality

  Background:
    Given I am on the Google homepage

  Scenario: Search for Selenium TestNG
    When I search for "Selenium TestNG"
    Then I should see search results
    And the page title should contain "Selenium TestNG"

  Scenario Outline: Search with different terms
    When I search for "<searchTerm>"
    Then I should see search results
    And the page title should contain "<expectedTitle>"

    Examples:
      | searchTerm      | expectedTitle |
      | Selenium        | Selenium      |
      | TestNG          | TestNG        |
      | Java            | Java          |
