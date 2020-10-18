import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartOneSteps {
    Game game = new Game();

    @Given("I roll three skulls")
    public void iRollThreeSkulls() {
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Skull); add(Dice.Skull); add(Dice.Diamond); add(Dice.Parrot); add(Dice.Sword); add(Dice.Coin); add(Dice.Coin); }};
        game.RollDices(new int[]{1,2,3,4,5,6,7,8});
    }

    @Given("I roll three skulls after re-roll")
    public void iRollThreeSkullsAfterReRoll() {
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); }};
        game.RollDices(new int[]{1,2,3,4,5,6,7,8});
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Skull); add(Dice.Sword); }};
        game.RollDices(new int[]{6,7,8});
    }

    @Then("I should be dead")
    public void iShouldBeDead() {
        assertEquals(true, game.DidRollThreeSkulls());
        System.out.println("You died from rolling three skulls.");
    }
}
