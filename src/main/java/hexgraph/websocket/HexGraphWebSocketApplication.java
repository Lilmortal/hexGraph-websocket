package hexgraph.websocket;

import hexgraph.netty.websocket.server.Server;

public class HexGraphWebSocketApplication {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
