Feature: Advance Searching for Products

  @search
  Scenario: Refine Search Results
    Given User visit the Balsam Hill website
    When user navigates to "Artificial Christmas Trees"
    And refine the results using the following options
      | mainOption         | subOption   |
      | Decoration Options | Undecorated |
    And sort the results by "Price Low to High"
    Then user should see the following products in order
      | order | productName                |
      | 1     | Classic Blue Spruce        |
      | 3     | Classic Blue Spruce        |
      | 4     | Classic Blue Spruce        |
      | 10    | Frosted Alpine Balsam Tree |
