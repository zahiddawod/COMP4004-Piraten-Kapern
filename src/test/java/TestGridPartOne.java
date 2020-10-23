import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestGridPartOne {
    private ExecutorService executor;
    private EmulateServer server;

    public TestGridPartOne() {
        Client.TEST_MODE = false;
        Server.TEST_MODE = false;
        Game.TEST_MODE= true;
    }

    @Test
    public void Test40() {
        ShortGame("test40");
        assertEquals("p3test40", server.instance.GetWinner().GetName());
        assertEquals(1, server.instance.GetCurrentRound()-1);
    }

    @Test
    public void Test43() {
        ShortGame("test43");
        assertEquals("p2test43", server.instance.GetWinner().GetName());
        assertEquals(2, server.instance.GetCurrentRound()-1);
    }

    private void ShortGame(String testNo) {
        executor = Executors.newFixedThreadPool(Server.MAX_PLAYERS+1);
        server = new EmulateServer();
        executor.execute(server);
        EmulateClient player[] = new EmulateClient[Server.MAX_PLAYERS];
        for (int i = 0; i < Server.MAX_PLAYERS; i++) {
            Game.Sleep(100);
            player[i] = new EmulateClient();
            player[i].name = ("p" + (i+1) + testNo);
            executor.execute(player[i]);
        }
        executor.shutdown();
        while (!executor.isTerminated()) { } // Wait until all threads are finish
        for (int i = 0; i < Server.MAX_PLAYERS; i++) player[i].instance.Disconnect();
        server.instance.Shutdown();
        System.out.println("\nFinished executing all threads.");
    }

    @Test
    public void Test48() {
        Game game = new Game();
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Skull); add(Dice.Skull); add(Dice.Diamond); add(Dice.Parrot); add(Dice.Sword); add(Dice.Coin); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(0, game.UpdateScore());
    }

    @Test
    public void Test49() {
        Game game = new Game();
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Skull); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{6,7,8}));
        assertEquals(0, game.UpdateScore());
    }

    @Test
    public void Test50() {
        Game game = new Game();
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Skull); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Sword); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{7,8}));
        assertEquals(0, game.UpdateScore());
    }

    @Test
    public void Test52() {
        Game game = new Game();
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Monkey); add(Dice.Monkey); }};
        assertEquals(true, game.RollDices(new int[]{6,7,8}));
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Monkey); }};
        assertEquals(true, game.RollDices(new int[]{7,8}));
        assertEquals(0, game.UpdateScore());
    }

    @Test
    public void Test53() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Captain);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Diamond); add(Dice.Diamond); add(Dice.Coin); add(Dice.Coin); add(Dice.Parrot); add(Dice.Sword); add(Dice.Monkey); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(800, game.UpdateScore());
    }

    @Test
    public void Test54() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Skull); add(Dice.Sword); add(Dice.Parrot); add(Dice.Sword); add(Dice.Parrot); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(100, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{4,5}));
        assertEquals(200, game.UpdateScore());
    }

    @Test
    public void Test55() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Parrot); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(300, game.UpdateScore());
    }

    @Test
    public void Test56() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Sword); add(Dice.Parrot); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(300, game.UpdateScore());
    }

    @Test
    public void Test57() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Diamond); add(Dice.Diamond); add(Dice.Diamond); add(Dice.Monkey); add(Dice.Parrot); add(Dice.Sword); add(Dice.Skull); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(500, game.UpdateScore());
    }

    @Test
    public void Test58() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Diamond);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Parrot); add(Dice.Sword); add(Dice.Skull); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(700, game.UpdateScore());
    }

    @Test
    public void Test59() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(400, game.UpdateScore());
    }

    @Test
    public void Test60() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Parrot); add(Dice.Parrot); add(Dice.Monkey); add(Dice.Sword); add(Dice.Parrot); add(Dice.Monkey); add(Dice.Sword); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(200, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Coin); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2}));
        assertEquals(400, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Coin); add(Dice.Sword); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{3,5,6}));
        assertEquals(800, game.UpdateScore());
    }

    @Test
    public void Test61() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Captain);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Coin); add(Dice.Parrot); add(Dice.Monkey); add(Dice.Sword); add(Dice.Parrot); add(Dice.Monkey); add(Dice.Sword); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(200, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Coin); add(Dice.Parrot); }};
        assertEquals(true, game.RollDices(new int[]{3,4}));
        assertEquals(600, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Parrot); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{6,7}));
        assertEquals(1200, game.UpdateScore());
    }

    @Test
    public void Test62() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Parrot); add(Dice.Monkey); add(Dice.Sword); add(Dice.Parrot); add(Dice.Monkey); add(Dice.Sword); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(100, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Sword); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{2,3}));
        assertEquals(300, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Sword); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{6,7}));
        assertEquals(600, game.UpdateScore());
    }

    @Test
    public void Test63() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Skull); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(1100, game.UpdateScore());
    }

    @Test
    public void Test64() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(2100, game.UpdateScore());
    }

    @Test
    public void Test65() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(5400, game.UpdateScore());
    }

    @Test
    public void Test66() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Diamond);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(5400, game.UpdateScore());
    }

    @Test
    public void Test67() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Captain);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(9000, game.UpdateScore());
    }

    @Test
    public void Test68() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Diamond); add(Dice.Sword); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Sword); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(300, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Sword); add(Dice.Diamond); add(Dice.Parrot); add(Dice.Parrot); }};
        assertEquals(true, game.RollDices(new int[]{3,4,5,6,7,8}));
        assertEquals(400, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); }};
        assertEquals(true, game.RollDices(new int[]{5,6,7,8}));
        assertEquals(4600, game.UpdateScore());
    }

    @Test
    public void Test69() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Diamond);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Diamond); add(Dice.Sword); add(Dice.Parrot); add(Dice.Sword); add(Dice.Parrot); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(200, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Parrot); add(Dice.Sword); add(Dice.Diamond); add(Dice.Parrot); }};
        assertEquals(true, game.RollDices(new int[]{4,5,6,7}));
        assertEquals(400, game.UpdateScore());
    }

    @Test
    public void Test70() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Monkey); add(Dice.Sword); add(Dice.Sword); add(Dice.Parrot); add(Dice.Monkey); add(Dice.Parrot); add(Dice.Diamond); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(200, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Diamond); add(Dice.Diamond); }};
        assertEquals(true, game.RollDices(new int[]{5,7}));
        assertEquals(500, game.UpdateScore());
    }

    @Test
    public void Test71() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Coin); add(Dice.Sword); add(Dice.Parrot); add(Dice.Skull); add(Dice.Parrot); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(200, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Coin); add(Dice.Sword); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{4,5,7}));
        assertEquals(600, game.UpdateScore());
    }

    @Test
    public void Test72() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Diamond);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Coin); add(Dice.Sword); add(Dice.Parrot); add(Dice.Skull); add(Dice.Parrot); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(200, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Coin); add(Dice.Sword); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{4,5,7}));
        assertEquals(500, game.UpdateScore());
    }

    @Test
    public void Test73() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Coin); add(Dice.Coin); add(Dice.Skull); add(Dice.Skull); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(600, game.UpdateScore());
    }

    @Test
    public void Test75() {
        Game game = new Game();
        game.SetDrawnCard(FortuneCard.Coin);
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Coin); }};
        assertEquals(true, game.RollDices(new int[]{1,2,3,4,5,6,7,8}));
        assertEquals(2700, game.UpdateScore());
        game.desiredDices = new ArrayList<Dice>() {{ add(Dice.Diamond); }};
        assertEquals(false, game.RollDices(new int[]{8}));
    }
}

