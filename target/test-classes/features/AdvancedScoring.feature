Feature: Advanced Scoring

  # sorceress #
  @test70
  Scenario: Roll 2 skulls, re-roll one of them with FC as Sorceress
    Given My fortune card is "Sorceress"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Skull,Monkey,Parrot,Parrot,Sword,Sword,Sword"
    And I re-roll dices at "2,3" with "Sword,Sword"
    Then Dice 2 should be "Sword"

  @test71
  Scenario: Roll no skulls, then go next round and roll 1 skull and re-roll it, then score
    Given My fortune card is "Sorceress"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Parrot,Monkey,Parrot,Parrot,Coin,Sword,Sword"
    Then Go to next round
    When My fortune card is "Sorceress"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Parrot,Monkey,Parrot,Parrot,Coin,Sword,Sword"
    Then I re-roll dices at "1,2" with "Monkey,Monkey"

  @test72
  Scenario: Roll no skulls, then go next round and roll 1 skull and re-roll it, then go to next round
    Given My fortune card is "Sorceress"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Parrot,Monkey,Parrot,Parrot,Coin,Sword,Sword"
    Then Go to next round
    When My fortune card is "Sorceress"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Parrot,Monkey,Parrot,Parrot,Coin,Sword,Sword"
    And I re-roll dices at "1,2" with "Monkey,Monkey"
    Then Go to next round

  # monkey business #
  @test75
  Scenario: First roll get 3 Monkeys, 3 Parrots, 1 Skull, and 1 Coin
    Given My fortune card is "MonkeyBusiness"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Parrot,Parrot,Parrot,Skull,Coin"
    Then Score should be 1100

  @test76
  Scenario: Over several rolls get 2 Monkeys, 1 Parrot, 2 Coins, 1 Diamond, and 2 Swords
    Given My fortune card is "MonkeyBusiness"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Parrot,Coin,Coin,Diamond,Sword,Sword"
    Then Score should be 400

  @test77
  Scenario: Over several rolls get 3 Monkeys, 4 Parrots, 1 Sword
    Given My fortune card is "MonkeyBusiness"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Parrot,Parrot,Parrot,Parrot,Sword"
    Then Score should be 2000

  # full chest #
  @test91
  Scenario: Get 3 Monkeys, 3 Swords, 1 Diamond, and 1 Parrot with FC as Coin
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Sword,Sword,Sword,Diamond,Parrot"
    Then Score should be 400

  @test92
  Scenario: Get 3 Monkeys, 3 Swords, and 2 Coins with FC as Captain
    Given My fortune card is "Captain"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Sword,Sword,Sword,Coin,Coin"
    Then Score should be 1800

  @test93
  Scenario: Get 3 Monkeys, 4 Swords, and 1 Diamond with FC as Coin
    Given My fortune card is "Coin"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Sword,Sword,Sword,Sword,Diamond"
    Then Score should be 1000

  @test94
  Scenario: First roll get 4 Monkeys, 1 Sword, 2 Parrots, and 1 Coin then re-roll the 2 Parrots and get Coin, Sword with FC as 2-Sword-Sea-Battle
    Given My fortune card is "SabreTwo"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Monkey,Sword,Parrot,Parrot,Coin"
    And I re-roll dices at "6,7" with "Coin,Sword"
    Then Score should be 1200

  @test97
  Scenario: Roll and get 2 Monkeys, 1 Parrot, 2 Coins, and 3 Diamonds with FC as Monkey Business
    Given My fortune card is "MonkeyBusiness"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Parrot,Coin,Coin,Diamond,Diamond,Diamond"
    Then Score should be 1200

  # Skull Island and Skull Fortune Cards
  @test100
  Scenario: Die from rolling 1 Skull and having FC as two-skull
    Given My fortune card is "SkullTwo"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Monkey,Parrot,Coin,Coin,Diamond,Diamond,Diamond"
    Then I should be dead

  @test101
  Scenario: Die from rolling 2 Skulls and having FC as one-skull
    Given My fortune card is "SkullOne"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Skull,Parrot,Coin,Coin,Diamond,Diamond,Diamond"
    Then I should be dead

  # NEGATIVE MEANS SERVER WILL DEAL WITH REMOVING POINTS FROM OTHERS!!
  @test102
  Scenario: Roll 5 Skulls with FC as Captain
    Given My fortune card is "Captain"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Skull,Skull,Skull,Skull,Diamond,Diamond,Diamond"
    Then Score should be -1000

  @test103
  Scenario: Roll 2 Skulls with FC as two-skull, then re-roll and get 2 more Skulls, then re-roll and get 1 more Skull
    Given My fortune card is "SkullTwo"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Skull,Monkey,Parrot,Parrot,Diamond,Diamond,Diamond"
    And I re-roll dices at "3,4" with "Skull,Skull"
    And I re-roll dices at "5,6" with "Diamond,Skull"
    Then Score should be -700

  @test104
  Scenario: Roll 3 Skulls with FC as two-skull, then re-roll with no Skulls
    Given My fortune card is "SkullTwo"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Skull,Skull,Parrot,Parrot,Diamond,Diamond,Diamond"
    And I re-roll dices at "4,5" with "Diamond,Diamond"
    Then Score should be -500

  @test105
  Scenario: Roll 3 Skulls with FC as one-skull, then re-roll with 1 Skull, then re-roll with none
    Given My fortune card is "SkullOne"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Skull,Skull,Parrot,Parrot,Diamond,Diamond,Diamond"
    And I re-roll dices at "4,5" with "Diamond,Skull"
    And I re-roll dices at "4,6" with "Parrot,Parrot"
    Then Score should be -500

  @test106
  Scenario: Show deduction received cannot make your score negative
    Given My score is 200
    When I lost 500 points
    Then Player score should be 0

  # sea battles #
  @test109
  Scenario: Die on first roll with FC as 2-sword-sea-battle
    Given My fortune card is "SabreTwo"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Skull,Skull,Parrot,Parrot,Diamond,Diamond,Diamond"
    Then I should be dead
    And I should lose 300 points

  @test110
  Scenario: Die on first roll with FC as 3-sword-sea-battle
    Given My fortune card is "SabreThree"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Skull,Skull,Parrot,Parrot,Diamond,Diamond,Diamond"
    Then I should be dead
    And I should lose 500 points

  @test111
  Scenario: Die on first roll with FC as 4-sword-sea-battle
    Given My fortune card is "SabreFour"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Skull,Skull,Parrot,Parrot,Diamond,Diamond,Diamond"
    Then I should be dead
    And I should lose 1000 points

  @test112
  Scenario: Show deduction received cannot make your score negative part 2
    Given My score is 500
    When My fortune card is "SabreFour"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Skull,Skull,Skull,Parrot,Parrot,Diamond,Diamond,Diamond"
    Then I lost 1000 points
    Then Player score should be 0

  @test113
  Scenario: Roll 3 Monkeys, 2 Swords, 1 Coin, and 2 Parrots with FC as 2-sword-sea-battle
    Given My fortune card is "SabreTwo"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Sword,Sword,Coin,Parrot,Parrot"
    Then Score should be 500

  @test115
  Scenario: Roll 4 Monkeys, 1 Sword, 1 Skull, and 2 Parrots then re-roll the 2 parrots and get 1 Sword, 1 Skull with FC as 2-sword-sea-battle
    Given My fortune card is "SabreTwo"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Monkey,Sword,Skull,Parrot,Parrot"
    And I re-roll dices at "7,8" with "Sword,Skull"
    Then Score should be 500

  @test116
  Scenario: Roll 3 Monkeys, 4 Swords with FC as 3-sword-sea-battle
    Given My fortune card is "SabreThree"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Sword,Sword,Sword,Sword,Parrot"
    Then Score should be 800

  @test118
  Scenario: Roll 4 Monkeys, 2 Swords, and 2 Skulls then re-roll the 4 Monkeys and get 2 Skulls, 2 Swords with FC as 3-sword-sea-battle
    Given My fortune card is "SabreThree"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Monkey,Sword,Sword,Skull,Skull"
    And I re-roll dices at "1,2,3,4" with "Skull,Skull,Sword,Sword"
    Then I should be dead

  @test119
  Scenario: Roll 3 Monkeys, 4 Swords, and 1 Skull with FC as 4-sword-sea-battle
    Given My fortune card is "SabreFour"
    And I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Sword,Sword,Sword,Sword,Skull"
    Then Score should be 1300

  @test122
  Scenario: Roll 3 Monkeys, 1 Sword, 1 Skull, 1 Diamond, and 2 Parrots then re-roll the Parrots and get 2 Swords then re-roll the Monkeys and get 1 Sword, 2 Parrots
    Given My fortune card is "SabreFour"
    When I roll dices at "1,2,3,4,5,6,7,8" with "Monkey,Monkey,Monkey,Sword,Skull,Diamond,Parrot,Parrot"
    And I re-roll dices at "7,8" with "Sword,Sword"
    And I re-roll dices at "1,2,3" with "Sword,Parrot,Parrot"
    Then Score should be 1300