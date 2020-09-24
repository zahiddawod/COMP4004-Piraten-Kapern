import java.io.*;
import java.util.*;

public class Game implements Serializable {
    private static final long SERIAL_VERSION_UID = 1L;
    public static final int CLEAR_CONSOLE_LENGTH = 50;
    public static final String TITLE = "-=Piraten Kapern=-";
    public static final Client client = new Client("");

    public boolean isGameOver;

    public static void main(String[] args) {
        ClearConsole();
        System.out.println(TITLE);
        System.out.println("What would you like your username to be?");
        client.player.ChangeName("");
        Menu();
    }

    public Game() {
        this.isGameOver = false;
    }

    public void GameLoop() {
        while (!isGameOver) {
        }
    }

    public static void Menu() {
        if (client.isConnected) {
            client.isConnected = false;
            if (Client.TEST_MODE) return;
        }

        Scanner input = new Scanner(System.in);
        int option;
        do {
            ClearConsole();
            System.out.println(TITLE);
            System.out.println("Logged in as: " + client.player.GetName());
            System.out.println("1. Join Server");
            System.out.println("2. Change Name");
            System.out.println("0. Quit");
            option = input.nextInt();
            if (option == 0) System.exit(0);
            else if (option == 1) {
                System.out.print("Server IP: ");
                Scanner i = new Scanner(System.in);
                String IP = i.nextLine();
                client.Join(IP);
            } else if (option == 2)  {
                client.player.ChangeName("");
                Menu();
            }
        } while (option < 1 || option > 3);
    }

    public Player GetWinner(Player[] players) {
        Player winner = players[0];
        for (int i = 0; i < players.length; i++) {
            if (players[i].GetScore() > winner.GetScore())
                winner = players[i];
        }
        return winner;
    }

    public static void ClearConsole() { for (int i = 0; i < CLEAR_CONSOLE_LENGTH; i++) System.out.println("\b");  }
    public static void Sleep(int milliseconds) { try { Thread.sleep(milliseconds); } catch (InterruptedException ie) { ie.printStackTrace(); } }

}
