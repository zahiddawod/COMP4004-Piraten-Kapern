import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PartOneSteps {
    Game game = new Game();

    @Given("I roll three skulls")
    public void iRollThreeSkulls() {
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Skull); add(Dice.Skull); add(Dice.Diamond); add(Dice.Parrot); add(Dice.Sword); add(Dice.Coin); add(Dice.Coin); }};
        game.RollDices(new int[]{1,2,3,4,5,6,7,8});
    }

    @Given("I roll dices at {string} with {string}")
    public void iRollDicesAtWith(String arg0, String arg1) {
        game.desiredDices = StringToDiceArrayList(arg1);
        game.RollDices(WhichDicesToRoll(arg0));
    }

    @And("I re-roll dices at {string} with {string}")
    public void iReRollDicesAtWith(String arg0, String arg1) {
        System.out.println("\nRE-ROLL...");
        iRollDicesAtWith(arg0, arg1);
    }

    @When("My fortune card is {string}")
    public void myFortuneCardIs(String arg0) {
        System.out.println();
        game.SetDrawnCard(FortuneCard.valueOf(arg0));
    }

    @Then("I should be dead")
    public void iShouldBeDead() {
        assertTrue(game.DidRollThreeSkulls() || (game.InSkullIsland() && !game.DidRollAtLeastOneSkull()));
        System.out.println("You died " + (game.InSkullIsland() ? "in Skull Island by not rolling at least one skull." : "from rolling three skulls."));
    }

    @Then("Score should be {int}")
    public void scoreShouldBe(int expectedScore) {
        assertEquals(expectedScore, game.potentialScore);
    }

    private ArrayList<Dice> StringToDiceArrayList(String str) {
        ArrayList<Dice> list = new ArrayList<Dice>();
        String[] strArray = str.trim().split("\\s*,\\s*");
        for (int i = 0; i <strArray.length; i++)
            list.add(Dice.valueOf(strArray[i]));
        return list;
    }

    private int[] WhichDicesToRoll(String str) {
        String[] tmpStr = str.trim().split("\\s*,\\s*");
        int[] whichDices = new int[tmpStr.length];
        for (int i = 0; i < tmpStr.length; i++)
            whichDices[i] = Integer.parseInt(tmpStr[i]);
        return whichDices;
    }
}
