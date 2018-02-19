package hexgraph.websocket.config;

public interface Configuration {
    int getPort();

    String getDirectory();

    String getCacheUri();

    String getDatabaseNode();

    Integer getDatabasePort();

    String getDatabaseName();

    String getDatabaseReplicationStrategy();

    Integer getDatabaseReplicationFactor();
}
