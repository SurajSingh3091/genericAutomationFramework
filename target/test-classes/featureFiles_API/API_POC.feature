Feature: To test API

  @complexJSON
  Scenario: User wants to test the complex JSON
    Given user had the complex JSON as response
    When user wants print No of courses returned by API
    Then user Print Purchase Amount
    And user Print Title of the first course
    And user Print All course titles and their respective Prices
    And user Print no of copies sold by RPA Course
    And finally user Verify if Sum of all Course prices matches with Purchase Amount

    @addBook
  Scenario: User wants to add, get and the delete the book in Library
    Given User does the pre setup for the library application
    When User adds the book
    Then User validates that book is added successfully
    And Then user deletes the book.

      @CreatebugJira
      Scenario: User wants to create an Issue and attache all the screenshots
        Given User does the pre setup for authentication to create bug
        When User Creates the bug and validates if it is created and then extracts the Id
        Then User does pre setup for authentication to attach the bug
        And validates that the attachment is sccuess.