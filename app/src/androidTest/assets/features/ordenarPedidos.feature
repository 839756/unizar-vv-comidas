Feature: The App lets the user arrange the orders

  The user can select the way the orders are displayed,
  the number of orders are not altered

  Scenario: Arrange orders
    Given There are 3 orders
    When Arrenge the orders
    Then There should be 3 orders