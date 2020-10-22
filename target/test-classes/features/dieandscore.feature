Feature: Basic Dying and Scoring

  @test38
  Scenario: Die with 3 skulls on first roll
    Given I roll three skulls
    Then I should be dead

  @test39
  Scenario: Start with 1 skull and die after re-rolling and getting 2 skulls
    Given I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Parrot,Parrot,Parrot,Parrot,Sword,Sword,Sword"
    And I re-roll dices at "6,7,8" with "Skull,Skull,Sword"
    Then I should be dead

  @test40
  Scenario: Start with 2 skulls and die after re-rolling and getting 1 more skull
    Given I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Skull,Parrot,Parrot,Parrot,Parrot,Sword,Sword"
    And I re-roll dices at "7,8" with "Skull,Sword"
    Then I should be dead

  @test42
  Scenario: Start with 1 skull and re-roll twice until we eventually die from obtaining 3 skulls
    Given I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Parrot,Parrot,Parrot,Parrot,Sword,Sword,Sword"
    And I re-roll dices at "6,7,8" with "Skull,Monkey,Monkey"
    And I re-roll dices at "7,8" with "Skull,Monkey"
    Then I should be dead

  @test43
  Scenario: Score first roll with 2 diamonds, 2 coins and Captain as FC
    Given My fortune card is "Captain"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Diamond,Diamond,Coin,Coin,Parrot,Skull,Skull,Sword"
    Then Score should be 800

  @test44
  Scenario: Get 2 monkeys on 1st roll, and 1 monkey on 2nd roll with Coin as FC
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Skull,Parrot,Sword,Skull,Sword,Parrot"
    And I re-roll dices at "4,5" with "Monkey,Parrot"
    Then Score should be 200

  @test45
  Scenario: Score with 2 sets of 3 (Monkey and Swords) on first roll
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Sword,Sword,Sword,Skull,Parrot"
    Then Score should be 300

  @test46
  Scenario: Score with 2 sets of 3 (Monkey and Parrots) over 2 rolls
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Parrot,Sword,Sword,Skull,Skull"
    And I re-roll dices at "5,6" with "Parrot,Parrot"
    Then Score should be 300

  @test47
  Scenario: Score with a set of 3 Diamonds on first roll
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Diamond,Diamond,Diamond,Parrot,Sword,Sword,Skull,Skull"
    Then Score should be 500

  @test48
  Scenario: Score with a set of 4 Coins on first roll
    Given My fortune card is "Diamond"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Coin,Coin,Coin,Coin,Sword,Parrot,Skull,Skull"
    Then Score should be 700

  @test49
  Scenario: Score with a set of 3 Swords and 4 Parrots on first roll
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Sword,Sword,Sword,Parrot,Parrot,Parrot,Parrot,Skull"
    Then Score should be 400

  @test50
  Scenario: Score set of 3 Coins and 4 Swords over several rolls with FC as Coin
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Sword,Parrot,Diamond,Diamond,Sword,Parrot,Parrot,Skull"
    And I re-roll dices at "2,3,4" with "Coin,Coin,Coin"
    And I re-roll dices at "6,7" with "Sword,Sword"
    Then Score should be 800

  @test51
  Scenario: Score set of 3 Coins and 4 Swords over several rolls with FC as Captain
    Given My fortune card is "Captain"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Sword,Parrot,Diamond,Diamond,Sword,Parrot,Parrot,Skull"
    And I re-roll dices at "2,3,4" with "Coin,Coin,Coin"
    And I re-roll dices at "6,7" with "Sword,Sword"
    Then Score should be 1200

  @test52
  Scenario: Score with 5 Swords over 3 rolls
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Sword,Parrot,Diamond,Parrot,Skull,Parrot,Parrot,Skull"
    And I re-roll dices at "2,3" with "Sword,Sword"
    And I re-roll dices at "6,7" with "Sword,Sword"
    Then Score should be 600

  @test53
  Scenario: Score with 6 Monkeys on first roll
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Monkey,Monkey,Monkey,Parrot,Skull"
    Then Score should be 1100

  @test54
  Scenario: Score with 7 Parrots on first roll
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Parrot,Parrot,Parrot,Parrot,Parrot,Parrot,Parrot,Skull"
    Then Score should be 2100

  @test55
  Scenario: Score with 8 Coins on first roll with FC as Coin
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Coin,Coin,Coin,Coin,Coin,Coin,Coin,Coin"
    Then Score should be 5400

  @test56
  Scenario: Score with 8 Coins on first roll with FC as Diamond
    Given My fortune card is "Diamond"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Coin,Coin,Coin,Coin,Coin,Coin,Coin,Coin"
    Then Score should be 5400

  @test57
  Scenario: Score with 8 Swords on first roll with FC as Captain
    Given My fortune card is "Captain"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Sword,Sword,Sword,Sword,Sword,Sword,Sword,Sword"
    Then Score should be 9000

  @test58
  Scenario: Score with 8 Monkeys over several rolls with FC as Coin
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Coin,Diamond,Parrot,Parrot,Coin,Monkey,Sword,Sword"
    And I re-roll dices at "1,2" with "Monkey,Monkey"
    And I re-roll dices at "3,4,5" with "Monkey,Monkey,Diamond"
    And I re-roll dices at "6,7" with "Monkey,Monkey"
    And I re-roll dices at "5,8" with "Monkey,Monkey"
    Then Score should be 4600

  @test59
  Scenario: Score with 2 Diamonds over 2 rolls with FC as Diamond
    Given My fortune card is "Diamond"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Diamond,Parrot,Parrot,Skull,Monkey,Monkey,Sword"
    And I re-roll dices at "3,4" with "Diamond,Sword"
    Then Score should be 400

  @test60
  Scenario: Score with 3 Diamonds over 2 rolls
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Diamond,Parrot,Parrot,Skull,Monkey,Monkey,Sword"
    And I re-roll dices at "3,4" with "Diamond,Diamond"
    Then Score should be 500

  @test61
  Scenario: Score with 3 Coins over 2 rolls
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Coin,Parrot,Parrot,Skull,Monkey,Monkey,Sword"
    And I re-roll dices at "3,4" with "Coin,Coin"
    Then Score should be 600

  @test62
  Scenario: Score with 3 Coins over 2 rolls with FC as Diamond
    Given My fortune card is "Diamond"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Coin,Parrot,Parrot,Skull,Monkey,Monkey,Sword"
    And I re-roll dices at "3,4" with "Coin,Coin"
    Then Score should be 500

  @test63
  Scenario: Score with 4 Monkeys and 3 Coins with FC as Coin
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Monkey,Coin,Coin,Sword,Sword"
    Then Score should be 600

  @test65
  Scenario: Score with 7 Swords on first roll, and try to re-roll 8th die
    Given I roll dices at "1,2,3,4,5,6,7,8" with "Sword,Sword,Sword,Sword,Sword,Sword,Sword,Monkey"
    Then I re-roll dices at "8" with "Sword"
    And Dice 8 should still be "Monkey"