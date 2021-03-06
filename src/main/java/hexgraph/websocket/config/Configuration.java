package hexgraph.websocket.config;

public interface Configuration {
    String getDirectory();

    Integer getChannelPort();

    String getCacheNode();

    Integer getCachePort();

    String getDatabaseNode();

    Integer getDatabasePort();

    String getDatabaseName();

    String getDatabaseReplicationStrategy();

    Integer getDatabaseReplicationFactor();
}
