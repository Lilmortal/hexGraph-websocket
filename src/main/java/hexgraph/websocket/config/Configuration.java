package hexgraph.websocket.config;

public interface Configuration {
    int getPort();

    String getDirectory();

    String getCacheUri();

    String getDatabaseNode();

    int getDatabasePort();
}
