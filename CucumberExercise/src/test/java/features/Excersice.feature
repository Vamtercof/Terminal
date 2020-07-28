Feature: Update user information

  Scenario: Update user Information in Preview section
    Given  open browser in Developer Guide
    And user Clicks on Component Reference tab
    And search in Quick Find for "datatable"
    And click on "Data Table with Inline Edit" option in ddl
    And click on Open in Playground button
    When third row information is updated
    Then verify that information has been saved correctly