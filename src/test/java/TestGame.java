import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.*;
import java.util.*;

public class TestGame {
    @Test
    public void TestWinner() {
        Game game = new Game();
        Game.client.playerList = new Player[]{ new Player("P1"), new Player("P2"), new Player("P3"), new Player("P4")};
        Game.client.playerList[0].SetScore(6200);
        Game.client.playerList[1].SetScore(5700);
        Game.client.playerList[2].SetScore(6000);
        Game.client.playerList[3].SetScore(500);
        assertEquals(Game.client.playerList[0], game.GetWinner(Game.client.playerList));
    }

    @Test
    @Timeout(1)
    public void TestSleep() {
        Game.Sleep(950);
    }

    @Test
    public void TestClearConsole() {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        PrintStream newStream = new PrintStream(o);
        PrintStream prevStream = System.out;

        System.setOut(newStream);
        Game.ClearConsole();
        System.out.flush();
        System.setOut(prevStream);

        String result = o.toString();
        while (result.contains("\b"))
            result = o.toString().replaceAll("^\b+|[^\b]\b", "");

        assertEquals(Game.CLEAR_CONSOLE_LENGTH, result.length()-1);
    }

    @Test
    public void TestIsGameOver() {
        Game game = new Game();
        assertEquals(false, game.IsOver());
        game.End();
        assertEquals(true, game.IsOver());
    }

    @Test
    public void TestDidRollThreeSkulls() {
        Game game = new Game();
        ArrayList<Dice> dices = new ArrayList<>();
        dices.add(Dice.Diamond);
        dices.add(Dice.Diamond);
        dices.add(Dice.Coin);
        dices.add(Dice.Parrot);
        dices.add(Dice.Parrot);
        dices.add(Dice.Monkey);
        dices.add(Dice.Diamond);
        dices.add(Dice.Skull);
        game.SetDices(dices);
        assertEquals(false, game.DidRollThreeSkulls());
        dices.set(0, Dice.Skull);
        dices.set(1, Dice.Skull);
        game.SetDices(dices);
        assertEquals(true, game.DidRollThreeSkulls());
    }

    @Test
    public void TestResetDices() {
        Game game = new Game();
        game.RollDices(new int[]{1,2,3,4,5,6,7,8});
        game.ResetDices();
        for (int i = 0; i < 8; i++)
            assertEquals(Dice.Coin, game.GetDices().get(i));
    }

    @Test
    public void TestSetDices() {
        Game game = new Game();
        ArrayList<Dice> dices = new ArrayList<>();
        dices.add(Dice.Diamond);
        dices.add(Dice.Diamond);
        dices.add(Dice.Diamond);
        dices.add(Dice.Coin);
        dices.add(Dice.Parrot);
        dices.add(Dice.Monkey);
        dices.add(Dice.Skull);
        dices.add(Dice.Skull);
        game.SetDices(dices);
        assertEquals(dices, game.GetDices());
    }

    @Test
    public void TestUpdateScore() {
        Game game = new Game();
        ArrayList<Dice> dices = new ArrayList<>();
        dices.add(Dice.Diamond);
        dices.add(Dice.Diamond);
        dices.add(Dice.Diamond);
        dices.add(Dice.Coin);
        dices.add(Dice.Parrot);
        dices.add(Dice.Monkey);
        dices.add(Dice.Skull);
        dices.add(Dice.Skull);
        game.SetDices(dices);
        game.SetDrawnCard(FortuneCard.Captain);
        assertEquals(1000, game.UpdateScore());

        dices.clear();
        dices.add(Dice.Diamond);
        dices.add(Dice.Diamond);
        dices.add(Dice.Coin);
        dices.add(Dice.Coin);
        dices.add(Dice.Parrot);
        dices.add(Dice.Monkey);
        dices.add(Dice.Skull);
        dices.add(Dice.Skull);
        game.SetDices(dices);
        game.SetDrawnCard(FortuneCard.Diamond);
        assertEquals(600, game.UpdateScore());

        dices.clear();
        dices.add(Dice.Diamond);
        dices.add(Dice.Diamond);
        dices.add(Dice.Coin);
        dices.add(Dice.Diamond);
        dices.add(Dice.Skull);
        dices.add(Dice.Skull);
        dices.add(Dice.Monkey);
        dices.add(Dice.Skull);
        game.SetDices(dices);
        game.SetDrawnCard(FortuneCard.Coin);
        assertEquals(0, game.UpdateScore());
    }

    @Test
    public void TestRandomRoll() {
        Game game = new Game();
        assertEquals(true, game.RollDices(new int[]{1,2,3,7,6}));
        assertEquals(false, game.RollDices(new int[]{8}));
        assertEquals(false, game.RollDices(new int[]{1,2,3,4,5,6,7,8,9,10,11}));
    }

    @Test
    public void TestInitDeck() {
        Game game = new Game();
        game.InitializeDeck();
        assertEquals(35, game.GetDeck().size());
    }

    @Test
    public void TestDrawCard() {
        Game game = new Game();
        for (int i = 0; i < 36; i++)
            game.DrawCard();
        assertEquals(34, game.GetDeck().size());
    }
}
