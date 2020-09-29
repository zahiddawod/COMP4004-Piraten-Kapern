import java.awt.*;
import java.net.*;
import java.io.*;

public class Client {
    public static boolean TEST_MODE;

    public Player player;
    public Player playerList[];

    public boolean isConnected;

    private Socket socket;
    private Game game = new Game();

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Client(String playerName) {
        player = new Player(playerName);
        playerList = new Player[Server.MAX_PLAYERS];
        for (int i = 0; i < Server.MAX_PLAYERS; i++)
            playerList[i] = new Player("");
    }

    public void Join(String host) {
        if (host.equals("")) host = "127.0.0.1";

        int port;
        if (host.contains(":")) port = Integer.parseInt(host.substring(host.indexOf(":") + 1));
        else port = Server.PORT;

        System.out.println("Attempting to connect..");
        isConnected = true;
        try {
            socket = new Socket(host, port);
            System.out.println("Connected!");
            if (!TEST_MODE) {
                System.out.println("[SERVER] Waiting for more players..");
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                SendPlayer();
                player.SetID(GetInt());
                UpdatePlayerList();
                game.GameLoop();
            }
        } catch (IOException e) {
            System.out.println("Failed to connect.");
            Game.Sleep(1000);
            Game.Menu();
        }
    }

    public void Disconnect() {
        try {
            this.socket.close();
            isConnected = false;
            System.out.println("Disconnected from server.");
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void SendPlayer() throws IOException {
        out.writeObject(player);
        out.flush();
    }

    public void SendInt(int value) {
        try {
            out.writeInt(value);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FortuneCard GetCard() {
        try {
            return (FortuneCard) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void UpdatePlayerList() {
        try {
            for (int i = 0; i < playerList.length; i++)
                playerList[i] = (Player) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int GetInt() {
        int value = 0;
        try { value = in.readInt(); } catch (IOException e) { e.printStackTrace(); }
        return value;
    }
}
