import java.io.*;
import java.util.*;

public class Game implements Serializable {
    private static final long SERIAL_VERSION_UID = 1L;
    public static final int CLEAR_CONSOLE_LENGTH = 50;
    public static final String TITLE = "-=Piraten Kapern=-";
    public static final Client client = new Client("");
    public static boolean TEST_MODE = false;

    public int currentRound, potentialScore;
    public ArrayList<Dice> desiredDices;

    private boolean isGameOver, inSkullIsland, didRollAtLeastOneSkull;
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
        this.inSkullIsland = false;
        this.potentialScore = 0;
        ResetDices();
        InitializeDeck();
    }

    public void GameLoop(Client c) {
        if (c == null) c = Game.client;
        while (!isGameOver) {
            currentRound = c.GetInt();
            int playerTurn = c.GetInt();

            ClearConsole();
            System.out.println("******** Score Sheet ********");
            for (int i = 0; i < c.playerList.length; i++)
                System.out.println(c.playerList[i].GetName() + ": " + c.playerList[i].GetScore() + (c.playerList[i].GetID() == c.player.GetID() ? " (You)" : "") + (i == playerTurn ? " (Turn)" : ""));

            System.out.println("******** " + (currentRound == -1 ? "Final Round" : "Round " + currentRound) + " ********");
            if (c.player.GetID() == playerTurn) {
                // draw fortune card
                drawnCard = c.GetCard();

                inSkullIsland = false;
                if (TEST_MODE) {
                    if (c.player.GetName().contains("test40")) {
                        if (c.player.GetName().equals("p1test40"))
                            SetDices(new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Skull); add(Dice.Skull); add(Dice.Parrot); add(Dice.Sword); add(Dice.Coin); add(Dice.Coin); add(Dice.Diamond); }});
                        else if (c.player.GetName().equals("p2test40"))
                            SetDices(new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Parrot); add(Dice.Sword); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Sword); }});
                        else if (c.player.GetName().equals("p3test40")) {
                            drawnCard = FortuneCard.Captain;
                            SetDices(new ArrayList<Dice>() {{ add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); }});
                        }
                    } else if (c.player.GetName().contains("test43")) {
                        if (c.player.GetName().equals("p1test43"))
                            SetDices(new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Parrot); add(Dice.Skull); add(Dice.Parrot); add(Dice.Sword); add(Dice.Coin); add(Dice.Coin); add(Dice.Diamond); }});
                        else if (c.player.GetName().equals("p2test43")) {
                            drawnCard = FortuneCard.SkullTwo;
                            SetDices(new ArrayList<Dice>() {{ add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); add(Dice.Sword); }});
                        } else if (c.player.GetName().equals("p3test43"))
                            SetDices(new ArrayList<Dice>() {{ add(Dice.Skull); add(Dice.Parrot); add(Dice.Diamond); add(Dice.Parrot); add(Dice.Sword); add(Dice.Monkey); add(Dice.Monkey); add(Dice.Sword); }});
                    }
                    System.out.println("Fortune Card Dealt ~ " + drawnCard);
                    DisplayDices();
                } else {
                    System.out.println("Fortune Card Dealt ~ " + drawnCard);
                    RollDices(new int[]{1, 2, 3, 4, 5, 6, 7, 8}); // roll all 8 dice
                }

                int option = 0;
                do {
                    if (DidRollThreeSkulls() || (inSkullIsland && !didRollAtLeastOneSkull)) {
                        Sleep(2000);
                        break;
                    }
                    System.out.println("\nSelect an option:");
                    System.out.println("1. Re-roll");
                    System.out.println("0. End Turn");

                    Scanner input = new Scanner(System.in);
                    if (!TEST_MODE) option = input.nextInt();
                    if (option == 1) {
                        System.out.println("NOTE: You must re-roll at least 2 dices.");
                        System.out.println("Select which dice you would like to re-roll by it's corresponding number. (Example: '1,3,5')");
                        String line = "0";
                        String[] choices;
                        Scanner scanner = new Scanner(System.in);
                        do {
                            if (scanner.hasNext()) line = scanner.nextLine();
                            choices = line.trim().split("\\s*,\\s*");
                            if (choices.length == 1 && Integer.parseInt(choices[0]) == 0) break;
                        } while (choices.length < 2);

                        int[] dicesToReRoll = new int[choices.length];
                        for (int i = 0; i < choices.length; i++) {
                            dicesToReRoll[i] = Integer.parseInt(choices[i]);
                            if (dicesToReRoll[i] < 0 || dicesToReRoll[i] > 8) {
                                System.out.println(dicesToReRoll[i] + " is not a valid dice.");
                                dicesToReRoll = new int[]{0};
                                break;
                            }
                        }
                        RollDices(dicesToReRoll);
                    }
                } while (option != 0);
                int addToScore = UpdateScore();
                if (addToScore > 0) c.player.SetScore(c.player.GetScore() + addToScore);
                c.SendInt(addToScore);
            } else {
                System.out.println("Waiting for " + c.playerList[playerTurn].GetName() + " to complete their turn..");
            }
            ResetDices();
            for (int i = 0; i < c.playerList.length; i++) { // update player scores
                int score = c.GetInt();
                c.playerList[i].SetScore(score);
                if (c.playerList[i].GetID() == c.player.GetID()) c.player.SetScore(score);
            }
            isGameOver = (c.GetInt() > 0);
        }

        // display winner
        ClearConsole();
        System.out.println("******** Game Over ********");
        Player winner = GetWinner(c.playerList);
        System.out.println("******** Winner is " + winner.GetName() + "! ********");
        for (int i = 0; i < c.playerList.length; i++)
            System.out.println(c.playerList[i].GetName() + ": " + c.playerList[i].GetScore() + (c.player.GetID() == c.playerList[i].GetID() ? " (You)" : ""));

        if (!TEST_MODE) {
            // wait for user input to leave
            int option;
            Scanner input = new Scanner(System.in);
            do {
                System.out.println("0. Leave");
                option = input.nextInt();
            } while (option != 0);

            Menu();
        }
        c.Disconnect();
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
        if (dicesToRoll.length < 2 || dicesToRoll.length > 8) { // at least 2 dices must be rolled
            System.out.println("You must roll at least 2 dices!");
            return false;
        }
        Set<Integer> occurrence = new HashSet<>();
        int numOfSkulls = 0;
        for (int i = 0; i < dicesToRoll.length; i++) { // counting how many skulls are being attempted for re-roll or the same die more than once
            if (dices.get(dicesToRoll[i] - 1).equals(Dice.Skull)) numOfSkulls++;
            if (occurrence.contains(dicesToRoll[i])) {
                System.out.println("Cannot roll the same die more than once!");
                return false;
            } else occurrence.add(dicesToRoll[i]);
        }
        if (numOfSkulls > 0) { // if trying to re-roll a skull without sorceress or more than 1
            if (drawnCard == FortuneCard.Sorceress) {
                if (numOfSkulls == 1) drawnCard = null; // sorceress must be discarded after use
                else {
                    System.out.println("Can only re-roll one skull with Sorceress!");
                    return false;
                }
            } else {
                System.out.println("Cannot re-roll a skull!");
                return false;
            }
        }

        didRollAtLeastOneSkull = false;
        for (int i = 0; i < dicesToRoll.length; i++) {
            dices.set(dicesToRoll[i] - 1, (desiredDices != null ? desiredDices.get(i) : Dice.GetRandomDice()));
            if (dices.get(dicesToRoll[i] - 1) == Dice.Skull) didRollAtLeastOneSkull = true;
        }

        DisplayDices();
        UpdateScore();

        return true;
    }

    private void DisplayDices() {
        System.out.print("Dices Rolled ~ " + (DidRollThreeSkulls() ? "(SKULLED) ~ " : ""));
        for (int i = 0; i < dices.size(); i++)
            System.out.print((i+1) + ": " + dices.get(i) + (i != dices.size()-1 ? ", " : " \n"));
    }

    public int UpdateScore() {
        int totalScore = 0;
        HashMap<Dice, Integer> sets = new HashMap<>();
        for (Dice d : Dice.values()) sets.put(d, 0); // populate hashmap

        for (int i = 0; i < dices.size(); i++) // check how many times each dice occurs in set
            sets.replace(dices.get(i), sets.get(dices.get(i)) + 1);

        // check for bonus from drawn fortune card
        int amountToAddForSwords = 0;
        if (drawnCard == FortuneCard.Coin)
            sets.replace(Dice.Coin, sets.get(Dice.Coin) + 1);
        else if (drawnCard == FortuneCard.Diamond)
            sets.replace(Dice.Diamond, sets.get(Dice.Diamond) + 1);
        else if (drawnCard == FortuneCard.SkullOne)
            sets.replace(Dice.Skull, sets.get(Dice.Skull) + 1);
        else if (drawnCard == FortuneCard.SkullTwo)
            sets.replace(Dice.Skull, sets.get(Dice.Skull) + 2);
        else if (drawnCard == FortuneCard.SabreTwo) {
            totalScore += 300;
            amountToAddForSwords = 2;
        } else if (drawnCard == FortuneCard.SabreThree) {
            totalScore += 500;
            amountToAddForSwords = 3;
        } else if (drawnCard == FortuneCard.SabreFour) {
            totalScore += 1000;
            amountToAddForSwords = 4;
        }
        sets.replace(Dice.Sword, sets.get(Dice.Sword) + amountToAddForSwords);

        // player dies if 3 skulls were rolled else if more than 3 return -100 per skull
        int numOfSkulls = sets.get(Dice.Skull);
        if (numOfSkulls == 3) {
            this.potentialScore = (amountToAddForSwords == 0) ? 0 : -totalScore;
            return this.potentialScore;
        } else if (numOfSkulls > 3) {
            if (amountToAddForSwords != 0) return 0; // we're in sea battle and can't go to island of skull
            if (!inSkullIsland) {
                System.out.println("Welcome to the Island of Skull! \nNOTE: Skull Points is how many points the other players will lose! Try to get as many skulls as you can (1 skull = -100 points)");
                inSkullIsland = true;
            }
            this.potentialScore = -(numOfSkulls*100);
            if (drawnCard == FortuneCard.Captain) this.potentialScore *= 2;
            System.out.println("Skull Points: " + this.potentialScore);
            return this.potentialScore;
        }

        // check for sets of identical dices
        Map<Integer, Integer> pointsForIdentical = new HashMap<Integer, Integer>() {{ put(3, 100); put(4, 200); put(5, 500); put(6, 1000); put(7, 2000); put (8, 4000); put(9, 4000); }};
        boolean isFullChest = true;
        if (drawnCard == FortuneCard.MonkeyBusiness) {
            totalScore += pointsForIdentical.getOrDefault(sets.get(Dice.Monkey) + sets.get(Dice.Parrot), 0);
            for (Map.Entry<Dice, Integer> s : sets.entrySet()) {
                if ((s.getValue() == 1 || s.getValue() == 2) && (s.getKey() == Dice.Sword || s.getKey() == Dice.Skull)) isFullChest = false;
                if (s.getKey() != Dice.Monkey && s.getKey() != Dice.Parrot) totalScore += pointsForIdentical.getOrDefault(s.getValue(), 0);
            }
        } else {
            for (Map.Entry<Dice, Integer> s : sets.entrySet()) {
                if ((s.getValue() == 1 || s.getValue() == 2) && (s.getKey() == Dice.Parrot || s.getKey() == Dice.Monkey || s.getKey() == Dice.Sword || s.getKey() == Dice.Skull)) isFullChest = false;
                if ((drawnCard == FortuneCard.SabreTwo || drawnCard == FortuneCard.SabreThree || drawnCard == FortuneCard.SabreFour) && s.getKey() == Dice.Sword) totalScore += pointsForIdentical.getOrDefault(s.getValue()-amountToAddForSwords, 0);
                else totalScore += pointsForIdentical.getOrDefault(s.getValue(), 0);
            }
        }
        if (isFullChest) totalScore += 500;
        totalScore += sets.get(Dice.Diamond) * 100;
        totalScore += sets.get(Dice.Coin) * 100;

        if (drawnCard == FortuneCard.Captain) totalScore *= 2;

        System.out.println("Current Score: " + client.player.GetScore());
        System.out.println("Potential Score: " + (client.player.GetScore() + totalScore));

        this.potentialScore = totalScore;

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
        for (Dice d : dices) if (d.equals(Dice.Skull)) counter++;
        if (counter == 3 || (InSeaBattle() && counter >= 3)) return true;
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
    public void SetDrawnCard(FortuneCard card) {
        drawnCard = card;
        System.out.println("Fortune Card Dealt ~ " + drawnCard);
    }
    public FortuneCard GetDrawnCard() { return drawnCard; }
    public boolean IsOver() { return isGameOver; }
    public void End() { isGameOver = true; }
    public boolean InSkullIsland() { return inSkullIsland; }
    public boolean DidRollAtLeastOneSkull() { return didRollAtLeastOneSkull; }
    public boolean InSeaBattle() { return (drawnCard == FortuneCard.SabreTwo || drawnCard == FortuneCard.SabreThree || drawnCard == FortuneCard.SabreFour); }

    public static void ClearConsole() { for (int i = 0; i < CLEAR_CONSOLE_LENGTH; i++) System.out.println("\b");  }
    public static void Sleep(int milliseconds) { try { Thread.sleep(milliseconds); } catch (InterruptedException ie) { ie.printStackTrace(); } }
}