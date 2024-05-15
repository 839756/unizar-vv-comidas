Feature: The App lets the user arrange the dishes

  The user can select the way the dishes are displayed,
  the number of dishes are not altered

  Scenario: Arrange dishes
    Given There are 3 dishes
    When Arrenge the dishes
    Then There should be 3 dishes