package hexgraph.websocket.config;

public class ConfigurationImpl implements Configuration {
    ConfigurationSingleton configurationSingleton = ConfigurationSingleton.getInstance();

    @Override
    public int getPort() {
        return configurationSingleton.getPort();
    }

    @Override
    public String getDirectory() {
        return configurationSingleton.getDirectory();
    }

    @Override
    public String getCacheUri() {
        return configurationSingleton.getCacheUri();
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
