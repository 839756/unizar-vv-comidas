Feature: The app lets the user create an order

  The order is added to the database when the
  user creates an order fulfilling all the required
  information

  Scenario: Create an order
    Given There are 3 orders
    When Add an order
    Then There should be 4 orders