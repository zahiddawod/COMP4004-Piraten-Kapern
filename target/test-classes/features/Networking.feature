Feature: Networked Game Test

  @test124
  Scenario: Simulate Playing The Game
    Given Server is running
    And Player 1 joins server
    And Player 2 joins server
    And Player 3 joins server
    Then Wait for the game to finish
    Then Winner should be player 3