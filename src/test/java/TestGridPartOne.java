import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.awt.*;

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

    class EmulateServer extends Thread {
        public Server instance;
        public void run() { instance = new Server(); }
    }

    class EmulateClient extends Thread {
        public String name;
        public Client instance;

        public void run() {
            instance = new Client(name);
            instance.Join("localhost");
        }
    }
}
