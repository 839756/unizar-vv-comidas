Feature: The App lets the user modify an order

  The user can select an order from the list
  and modify it, thus the order will be modified
  and still in the database

  Scenario: Modify an order
    Given There are 3 orders
    When Modify an order
    Then There should be 3 orders