package hexgraph.websocket;


import hexgraph.websocket.server.Server;

public class HexGraphWebSocketApplication {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
