Feature: The App lets the user modify a dish

  The user can select a dish from the list
  and modify it, thus the dish will be modified
  and still in the database
  
  Scenario: Modify a dish
    Given There are 3 dishes
    When Modify a dish
    Then There should be 3 dishes