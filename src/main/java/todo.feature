Feature: Todo functionality

  Scenario Outline: Adding todo tasks

    Given user navigates to todo page
    When user creates <numberOf> tasks named <taskName>
    Then tasks with <taskName> and <numberOf> are created successfully
    And their count is <numberOf>

    Examples:
      | numberOf | taskName |
      | 1        | task1    |
      | 50       | do something   |

    Scenario: Removing just the even tasks

      Given user navigates to todo page
      When user creates 10 tasks
      And removes the even tasks
      Then only the odd tasks should remain out of 10 tasks


      Scenario: User enters a big task

        Given user navigates to todo page
        When user creates a task with 10000 symbols
        Then it should have no issues

      Scenario: Completed items go to completed and incompleted remain active
        Given user navigates to todo page
        When user creates 5 tasks
        And user checks 2 tasks
        Then 3 tasks should remain in active
        And 2 task should go to completed
