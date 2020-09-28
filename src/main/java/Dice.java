public enum Dice {
    Skull,
    Monkey,
    Parrot,
    Sword,
    Coin,
    Diamond;

    public static Dice GetRandomDice() { return values()[new java.util.Random().nextInt(values().length)]; }
}