Feature: The App lets the user delete a dish

  The user can select a dish from the list
  and delete it, thus the dish will be deleted
  from the database

  Scenario: Delete a dish
    Given There are 3 dishes
    When Delete a dish
    Then There should be 2 dishes