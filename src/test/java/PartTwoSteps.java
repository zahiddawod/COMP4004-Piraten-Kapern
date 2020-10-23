import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PartTwoSteps {
    private ExecutorService executor;
    private EmulateServer server;
    private EmulateClient player[];

    public PartTwoSteps() {
        Client.TEST_MODE = false;
        Server.TEST_MODE = false;
        Game.TEST_MODE= true;
    }

    @Given("Server is running")
    public void serverIsRunning() {
        executor = Executors.newFixedThreadPool(Server.MAX_PLAYERS+1);
        server = new EmulateServer();
        executor.execute(server);
        player = new EmulateClient[Server.MAX_PLAYERS];
    }

    @And("Player {int} joins server")
    public void playerJoinsServer(int i) {
        i--;
        player[i] = new EmulateClient();
        player[i].name = ("p" + (i+1) + "test40");
        executor.execute(player[i]);
        do Game.Sleep(1000);
        while (!player[i].instance.isConnected);
    }

    @Then("Wait for the game to finish")
    public void waitForTheGameToFinish() {
        System.out.println("\nGame in progress..\n");
        Game.Sleep(5000);
    }

    @Then("Winner should be player {int}")
    public void winnerShouldBePlayer(int i) {
        assertEquals(player[i-1].instance.player.GetName(), server.instance.GetWinner().GetName());
    }
}
