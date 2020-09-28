import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestNetworking {
    public TestNetworking() {
        Client.TEST_MODE = true;
        Server.TEST_MODE = true;
    }

    @Test
    public void TestHost() {
        Server server = new Server();
        assertEquals(true, server.IsRunning());
        server.Shutdown();
        assertEquals(false, server.IsRunning());
    }

    @Test
    public void TestJoin() {
        Server s = new Server();
        Client c = new Client("");
        c.Join("localhost");
        assertEquals(true, c.isConnected);
        c.Disconnect();
        assertEquals(false, c.isConnected);
        s.Shutdown();
    }
}
