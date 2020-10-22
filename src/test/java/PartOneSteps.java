import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PartOneSteps {
    Game game = new Game();
    Player testPlayer = new Player("Zahid");

    public PartOneSteps() {
        System.out.println("\n******** " + ("Round " + (game.currentRound+1)) + " ********");
    }

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

    @Given("My score is {int}")
    public void myScoreIs(int arg0) { testPlayer.SetScore(arg0); }

    @When("I lost {int} points")
    public void iLostPointsWhenMyScoreIs(int arg0) { testPlayer.DeductPoints(arg0); }

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
        System.out.println("You died " + (game.InSeaBattle() ? "from rolling 3 or more skulls during sea battle" : (game.InSkullIsland() ? "in Skull Island by not rolling at least one skull." : "from rolling three skulls.")));
    }

    @And("Dice {int} should still be {string}")
    @Then("Dice {int} should be {string}")
    public void diceShouldBe(int arg0, String arg1) {
        assertEquals(Dice.valueOf(arg1), game.GetDices().get(arg0-1));
    }

    @Then("Score should be {int}")
    public void scoreShouldBe(int expectedScore) {
        assertEquals(expectedScore, game.potentialScore);
    }
    @And("I should lose {int} points")
    public void iShouldLosePoints(int arg0) { scoreShouldBe(-arg0); }

    @Then("Go to next round")
    public void goToNextRound() {
        if (game.potentialScore <= 0) return;
        Game.client.player.SetScore(Game.client.player.GetScore() + game.potentialScore);
        game.currentRound++;
        System.out.println("\n******** " + ("Round " + (game.currentRound+1)) + " ********");
    }

    @Then("Player score should be {int}")
    public void playerScoreShouldBe(int arg0) { assertEquals(arg0, testPlayer.GetScore()); }

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
