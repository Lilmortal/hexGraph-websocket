package hexgraph.websocket.config;

public class ConfigurationImpl implements Configuration {
    ConfigurationSingleton configurationSingleton = ConfigurationSingleton.getInstance();

    @Override
    public String getDirectory() {
        return configurationSingleton.getDirectory();
    }

    @Override
    public Integer getChannelPort() {
        return configurationSingleton.getChannelPort();
    }

    @Override
    public String getCacheNode() {
        return configurationSingleton.getCacheNode();
    }

    @Override
    public Integer getCachePort() {
        return configurationSingleton.getCachePort();
    }

    @Override
    public String getDatabaseNode() {
        return configurationSingleton.getDatabaseNode();
    }

    @Override
    public Integer getDatabasePort() {
        return configurationSingleton.getDatabasePort();
    }

    @Override
    public String getDatabaseName() {
        return configurationSingleton.getDatabaseName();
    }

    @Override
    public String getDatabaseReplicationStrategy() {
        return configurationSingleton.getDatabaseReplicationStrategy();
    }

    @Override
    public Integer getDatabaseReplicationFactor() {
        return configurationSingleton.getDatabaseReplicationFactor();
    }
}
