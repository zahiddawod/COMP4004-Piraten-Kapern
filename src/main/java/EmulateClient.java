public class EmulateClient extends Thread {
    public String name;
    public Client instance;

    public void run() {
        instance = new Client(name);
        instance.Join("localhost");
    }
}