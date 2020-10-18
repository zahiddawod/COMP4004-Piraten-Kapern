Feature: Basic Dying and Scoring

  @test38
  Scenario: Die with 3 skulls on first roll
    Given I roll three skulls
    Then I should be dead

  @test39
  Scenario: Die with 3 skulls after re-roll
    Given I roll three skulls after re-roll
    Then I should be dead