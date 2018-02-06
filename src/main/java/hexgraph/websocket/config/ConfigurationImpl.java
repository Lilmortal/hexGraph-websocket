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
}
