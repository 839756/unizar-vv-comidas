Feature: The App lets the user create a dish

  The dish is added to the database when the
  user creates a dish fulfilling all the required
  information

  Scenario: Create a dish
    Given There are 3 dishes
    When Add a dish
    Then There should be 4 dishes