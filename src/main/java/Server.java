import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int MAX_PLAYERS = 2;
    public static final int PORT = 8080;
    public static boolean TEST_MODE;
    public static int numPlayers = 0;

    private boolean isRunning;

    private ServerSocket ss;
    private Game game = new Game();

    private ArrayList<SocketHandler> sockets = new ArrayList<>();
    private Player[] playerList = new Player[MAX_PLAYERS];
    private ExecutorService pool = Executors.newFixedThreadPool(MAX_PLAYERS);

    public static void main(String[] args) { new Server(); }

    public Server() {
        System.out.println("[SERVER] Initializing..");
        try {
            ss = new ServerSocket(PORT);
            isRunning = true;

            System.out.println("[SERVER] Successfully Started!");
            if (TEST_MODE) return;
            while (numPlayers < MAX_PLAYERS) {
                System.out.println("[SERVER] Waiting for " + (MAX_PLAYERS - numPlayers) + " more player" + (numPlayers == MAX_PLAYERS - 1 ? "" : "s") + "..");
                Socket socket = ss.accept();
                numPlayers++;
                SocketHandler socketThread = new SocketHandler(socket);

                // update player on server's playerList
                Player p = (Player) socketThread.in.readObject();
                System.out.println("[SERVER] " + p.GetName() + " has joined!");
                playerList[socketThread.playerId] = new Player(p.GetName());
                playerList[socketThread.playerId].SetID(socketThread.playerId);

                socketThread.SendInt(socketThread.playerId); // send player's id to client

                sockets.add(socketThread);
            }
            System.out.println("Game will begin shortly..");
            Game.Sleep(1500);
            Game.ClearConsole();

            for (int i = 0; i < sockets.size(); i++)
                pool.execute(sockets.get(i));

            Update();
        } catch (IOException | ClassNotFoundException ioe) {
            isRunning = false;
            System.out.println("Failed to start server.");
            ioe.printStackTrace();
        }
    }

    private void Update() {
        try {
            // update playerList for everyone
            for (SocketHandler s : sockets)
                s.SendPlayers(playerList);

            int currentRound = 0, playerTurn = 0, playerWhoFirstReached6000 = -1; // game starts at round 1 with first player being first one to join
            while (!game.IsOver()) {
                if (playerTurn == 0) System.out.println("******** Round " + (++currentRound) + " ********");

                // update players on round info
                for (int i = 0; i < numPlayers; i++) {
                    sockets.get(i).SendInt(currentRound);
                    sockets.get(i).SendInt(playerTurn);
                }

                // draw card and send it to the current player
                sockets.get(playerTurn).out.writeObject(game.DrawCard());
                sockets.get(playerTurn).out.flush();

                // wait for whoever turn it is to get their new score
                int result = sockets.get(playerTurn).GetInt();
                playerList[playerTurn].SetScore(playerList[playerTurn].GetScore() + result);

                // update player scores for everyone
                for (SocketHandler s : sockets)
                    for (int i = 0; i < sockets.size(); i++)
                        s.SendInt(playerList[i].GetScore());

                // results of that round
                if (result == 0) System.out.println(playerList[playerTurn].GetName() + " got SKULLED!");
                else System.out.println(playerList[playerTurn].GetName() + " ended their turn with a new score of " + playerList[playerTurn].GetScore());
                if (playerList[playerTurn].GetScore() >= 6000) { // someone reached 6000 initiate final round
                    playerWhoFirstReached6000 = playerTurn;
                    currentRound = -1;
                    System.out.println("******** Final Round ********");
                }
                playerTurn++;
                if (playerTurn >= numPlayers) playerTurn = 0; // loop back to the first player in the list
                if (playerTurn == playerWhoFirstReached6000) game.End(); // everyone played one last turn
                for (SocketHandler s : sockets) s.SendInt(game.IsOver() ? 1 : 0); // update everyone if the game is over
            }

            System.out.println("******** Game Over ********");
            Player winner = game.GetWinner(playerList);
            System.out.println("******** Winner is " + winner.GetName() + "! ********");
            for (int i = 0; i < playerList.length; i++)
                System.out.println(playerList[i].GetName() + ": " + playerList[i].GetScore());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Shutdown() {
        try {
            ss.close();
            isRunning = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean IsRunning() { return this.isRunning; }

    public class SocketHandler implements Runnable {
        private final Socket socket;
        private int playerId;

        private ObjectInputStream in;
        private ObjectOutputStream out;

        public SocketHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.playerId = Server.numPlayers - 1;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            try {
                while (true) {}
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                    in.close();
                } catch (IOException ioe) { ioe.printStackTrace(); }
            }
        }

        public void SendPlayers(Player[] players) {
            try {
                for (Player p : players) {
                    out.writeObject(p);
                    out.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void SendInt(int value) {
            try {
                out.writeInt(value);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public int GetInt() {
            int value = 0;
            try { value = in.readInt(); } catch (IOException e) { e.printStackTrace(); }
            return value;
        }
    }
}
