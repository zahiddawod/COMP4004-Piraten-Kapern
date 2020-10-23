public class EmulateServer extends Thread {
    public Server instance;
    public void run() { instance = new Server(); }
}
