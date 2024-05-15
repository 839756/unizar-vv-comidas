Feature: The App lets the user delete an order

  The user can select an order from the list
  and delete it, thus the order will be deleted
  from the database

  Scenario: Delete an order
    Given There are 3 orders
    When Delete an order
    Then There should be 2 orders