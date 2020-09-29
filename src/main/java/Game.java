import java.io.*;
import java.util.*;

public class Game implements Serializable {
    private static final long SERIAL_VERSION_UID = 1L;
    public static final int CLEAR_CONSOLE_LENGTH = 50;
    public static final String TITLE = "-=Piraten Kapern=-";
    public static final Client client = new Client("");

    private boolean isGameOver;
    private ArrayList<Dice> dices = new ArrayList<>();
    private ArrayList<FortuneCard> deck = new ArrayList<>();
    private FortuneCard drawnCard;

    public static void main(String[] args) {
        ClearConsole();
        System.out.println(TITLE);
        System.out.println("What would you like your username to be?");
        client.player.ChangeName("");
        Menu();
    }

    public Game() {
        this.isGameOver = false;
        ResetDices();
        InitializeDeck();
    }

    public void GameLoop() {
        while (!isGameOver) {
            int currentRound = client.GetInt();
            int playerTurn = client.GetInt();

            ClearConsole();
            System.out.println("******** Score Sheet ********");
            for (int i = 0; i < client.playerList.length; i++)
                System.out.println(client.playerList[i].GetName() + ": " + client.playerList[i].GetScore() + (client.playerList[i].GetName().equals(client.player.GetName()) ? " (You)" : "") + (i == playerTurn ? " (Turn)" : ""));

            System.out.println("******** Round " + currentRound + " ********");
            if (client.player.GetID() == playerTurn) {
                // draw fortune card
                drawnCard = client.GetCard();
                System.out.println("Fortune Card Dealt ~ " + drawnCard);

                // roll all 8 dice
                RollDices(new int[]{1,2,3,4,5,6,7,8});

                int option = 0;
                do {
                    if (DidRollThreeSkulls()) {
                        option = 0;
                        Sleep(2000);
                    } else {
                        // choose
                        System.out.println("\nSelect an option:");
                        System.out.println("1. Re-roll");
                        System.out.println("0. End Turn");

                        Scanner input = new Scanner(System.in);
                        option = input.nextInt();
                        if (option == 1) {
                            // ask which dices they want to re-roll
                            System.out.println("Select which dice you would like to re-roll by it's corresponding number. (EX: '1, 3, 4, 5')");

                            String line = "0";
                            String[] choices;
                            Scanner scanner = new Scanner(System.in);
                            do {
                                System.out.println("NOTE: You must re-roll at least 2 dices.");
                                if (scanner.hasNext()) line = scanner.nextLine();
                                choices = line.trim().split("\\s*,\\s*");
                                if (choices.length == 1 && Integer.parseInt(choices[0]) == 0) break;
                            } while (choices.length < 2);

                            int[] dicesToReRoll = new int[choices.length];
                            for (int i = 0; i < choices.length; i++)
                                dicesToReRoll[i] = Integer.parseInt(choices[i]);
                            RollDices(dicesToReRoll);
                        }
                    }
                } while (option != 0);
                client.player.SetScore(client.player.GetScore() + UpdateScore());
                client.SendInt(client.player.GetScore());
            } else {
                System.out.println("Waiting for " + client.playerList[playerTurn].GetName() + " to complete their turn..");
            }
            ResetDices();
            for (int i = 0; i < client.playerList.length; i++) // update player scores
                client.playerList[i].SetScore(client.GetInt());
            for (Player p : client.playerList) if (p.GetScore() >= 6000) isGameOver = true;
        }

        client.Disconnect();
        Menu();
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

    public boolean RollDices(int[] dicesToRoll) {
        if (dicesToRoll.length < 2 || dicesToRoll.length > 8) return false; // at least 2 dices is re-rolled
        for (int i = 0; i < dicesToRoll.length; i++) { // if trying to re-roll a skull
            if (dices.get(dicesToRoll[i] - 1).equals(Dice.Skull)) {
                System.out.println("Cannot re-roll a skull!");
                return false;
            }
        }

        for (int i = 0; i < dicesToRoll.length; i++)
            dices.set(dicesToRoll[i] - 1, Dice.GetRandomDice());
        System.out.print("Dices Rolled ~ " + (DidRollThreeSkulls() ? "(SKULLED) ~ " : ""));
        for (int i = 0; i < dices.size(); i++)
            System.out.print((i+1) + ": " + dices.get(i) + (i != dices.size()-1 ? ", " : " \n"));

        UpdateScore();

        return true;
    }

    public int UpdateScore() {
        int totalScore = 0;
        HashMap<Dice, Integer> sets = new HashMap<>();
        for (Dice d : Dice.values()) sets.put(d, 0); // populate hashmap

        for (int i = 0; i < dices.size(); i++) // check how many times each dice occurs in set
            sets.replace(dices.get(i), sets.get(dices.get(i)) + 1);

        // check for bonus from drawn fortune card
        if (drawnCard == FortuneCard.Coin)
            sets.replace(Dice.Coin, sets.get(Dice.Coin) + 1);
        else if (drawnCard == FortuneCard.Diamond)
            sets.replace(Dice.Diamond, sets.get(Dice.Diamond) + 1);
        else if (drawnCard == FortuneCard.SkullOne)
            sets.replace(Dice.Skull, sets.get(Dice.Skull) + 1);
        else if (drawnCard == FortuneCard.SkullTwo)
            sets.replace(Dice.Skull, sets.get(Dice.Skull) + 2);

        if (sets.get(Dice.Skull) >= 3) return 0; // player gets nothing if 3 or more skulls were drawn

        Map<Integer, Integer> pointsForIdentical = new HashMap<Integer, Integer>() {{ put(3, 100); put(4, 200); put(5, 500); put(6, 1000); put(7, 2000); put (8, 4000); }};
        for (Map.Entry<Dice, Integer> s : sets.entrySet()) // account for sets of identical dices
            totalScore += pointsForIdentical.getOrDefault(s.getValue(), 0);

        totalScore += sets.get(Dice.Diamond) * 100;
        totalScore += sets.get(Dice.Coin) * 100;

        if (drawnCard == FortuneCard.Captain) totalScore *= 2;

        System.out.println("Current Score: " + client.player.GetScore());
        System.out.println("Potential Score: " + (client.player.GetScore() + totalScore));

        return totalScore;
    }

    public void InitializeDeck() {
        deck.clear();
        for (int i = 0; i < 4; i++) {
            deck.add(FortuneCard.Coin);
            deck.add(FortuneCard.Diamond);
            deck.add(FortuneCard.Sorceress);
            deck.add(FortuneCard.Captain);
            deck.add(FortuneCard.TreasureChest);
            deck.add(FortuneCard.MonkeyBusiness);
            if (i < 3) deck.add(FortuneCard.SkullOne);
            if (i < 2) {
                deck.add(FortuneCard.SkullTwo);
                deck.add(FortuneCard.SabreTwo);
                deck.add(FortuneCard.SabreThree);
                deck.add(FortuneCard.SabreFour);
            }
        }
        Collections.shuffle(deck);
    }

    public void ResetDices() {
        dices.clear();
        for (int i = 0; i < 8; i++) dices.add(Dice.Coin);
    }

    public boolean DidRollThreeSkulls() {
        int counter = 0;
        if (drawnCard == FortuneCard.SkullOne) counter = 1;
        else if (drawnCard == FortuneCard.SkullTwo) counter = 2;
        for (Dice d : dices) {
            if (d.equals(Dice.Skull)) counter++;
            if (counter >= 3) return true;
        }
        return false;
    }

    public FortuneCard DrawCard() {
        if (deck.size() == 0) InitializeDeck();
        int topCard = deck.size()-1;
        drawnCard = deck.get(topCard);
        deck.remove(topCard);
        return drawnCard;
    }

    public void SetDices(ArrayList<Dice> list) { dices = list; }
    public ArrayList<Dice> GetDices() { return dices; }
    public ArrayList<FortuneCard> GetDeck() { return deck; }
    public void SetDrawnCard(FortuneCard card) { drawnCard = card; }
    public boolean IsOver() { return isGameOver; }
    public void End() { isGameOver = true; }

    public static void ClearConsole() { for (int i = 0; i < CLEAR_CONSOLE_LENGTH; i++) System.out.println("\b");  }
    public static void Sleep(int milliseconds) { try { Thread.sleep(milliseconds); } catch (InterruptedException ie) { ie.printStackTrace(); } }
}