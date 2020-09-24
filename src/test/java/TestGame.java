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
}
