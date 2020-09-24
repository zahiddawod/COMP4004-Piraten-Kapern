import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestPlayer {
    private Player p = new Player("Test");

    @Test
    public void TestInit() {
        Player p2 = new Player("Test 2");
        assertEquals("Test 2", p2.GetName());
        assertEquals(Player.currentPlayerId, p2.GetID());
        p2.SetID(24);
        assertEquals(24, p2.GetID());
    }

    @Test
    public void TestScore() {
        p.SetScore(250);
        assertEquals(250, p.GetScore());
    }

    @Test
    public void TestEditName() {
        String newName = "Zahid";
        p.ChangeName(newName);
        assertEquals(newName, p.GetName());
    }

    @Test
    public void TestWinner() {
        p.SetScore(6000);
        assertEquals(true, p.IsWinner());
    }
}
