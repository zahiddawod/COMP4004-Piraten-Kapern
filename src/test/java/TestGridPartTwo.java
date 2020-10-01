import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestGridPartTwo {
    @Test
    public void Test80() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Sorceress);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Skull); add(Dice.Diamond); add(Dice.Diamond); add(Dice.Parrot); add(Dice.Sword); add(Dice.Coin); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(400, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Diamond); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{2,5}));
        assertEquals(600, game.UpdateScore());
    }

    @Test
    public void Test81() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Sorceress);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Parrot); add(Dice.Sword); add(Dice.Diamond); add(Dice.Diamond); add(Dice.Parrot); add(Dice.Sword); add(Dice.Coin); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(400, game.UpdateScore());
        game.ResetDices(); // next round
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Sword); add(Dice.Diamond); add(Dice.Diamond); add(Dice.Parrot); add(Dice.Sword); add(Dice.Coin); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(400, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Diamond); add(Dice.Diamond); }};
        assertEquals(true, game.RollDices(new int[]{1,2}));
        assertEquals(800, game.UpdateScore());
    }

    @Test
    public void Test82() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Sorceress);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Parrot); add(Dice.Sword); add(Dice.Diamond); add(Dice.Diamond); add(Dice.Parrot); add(Dice.Sword); add(Dice.Coin); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(400, game.UpdateScore());
        game.ResetDices(); // next round
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Sword); add(Dice.Diamond); add(Dice.Diamond); add(Dice.Parrot); add(Dice.Sword); add(Dice.Coin); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(400, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Diamond); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2}));
        assertEquals(600, game.UpdateScore());
    }

    @Test
    public void Test85() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.MonkeyBusiness);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Skull); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(1100, game.UpdateScore());
    }

    @Test
    public void Test86() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.MonkeyBusiness);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Parrot); add(Dice.Coin); add(Dice.Parrot); add(Dice.Sword); add(Dice.Sword); add(Dice.Monkey); add(Dice.Diamond); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(900, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Diamond); }};
        assertEquals(true, game.RollDices(new int[]{5,8}));
        assertEquals(500, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Coin); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{3,7}));
        assertEquals(400, game.UpdateScore());
    }

    @Test
    public void Test87() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.MonkeyBusiness);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(5300, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3}));
        assertEquals(1600, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Sword); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{7,8}));
        assertEquals(500, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); }};
        assertEquals(true, game.RollDices(new int[]{4,5,6,7}));
        assertEquals(2000, game.UpdateScore());
    }

    @Test
    public void Test101() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Diamond); add(Dice.Parrot); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(400, game.UpdateScore());
    }

    @Test
    public void Test102() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Captain);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Coin); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(1800, game.UpdateScore());
    }

    @Test
    public void Test103() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Diamond); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(1000, game.UpdateScore());
    }

    @Test
    public void Test104() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.SabreTwo);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Sword); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(600, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Coin); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{6,7}));
        assertEquals(1200, game.UpdateScore());
    }

    @Test
    public void Test107() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.MonkeyBusiness);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Parrot); add(Dice.Coin); add(Dice.Coin); add(Dice.Diamond); add(Dice.Diamond); add(Dice.Diamond); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(1200, game.UpdateScore());
    }
}
