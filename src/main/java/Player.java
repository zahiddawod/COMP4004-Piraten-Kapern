import java.io.Serializable;
import java.util.Scanner;

public class Player implements Serializable {
    private static final long SERIAL_VERSION_UID = 1L;
    public static int currentPlayerId = -1;

    private String name;
    private int score;
    private int myId;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        myId = ++currentPlayerId;
    }

    // Getters
    public String GetName() { return this.name; }
    public int GetScore() { return this.score; }
    public boolean IsWinner() { return (this.score >= 6000); }
    public int GetID() { return this.myId; }

    // Setters
    public void ChangeName(String name) {
        if (name.equals("")) {
            Scanner i = new Scanner(System.in);
            System.out.print("> ");
            name = i.nextLine();
        }
        this.name = name;
    }
    public void SetScore(int score) { this.score = score; }
    public void SetID(int id) { this.myId = id; }
    public void DeductPoints(int amount) {
        this.score -= amount;
        if (this.score < 0) this.score = 0;
        System.out.println(this.name + " lost " + amount + " points. New Score: " + this.score);
    }
}
