package hexgraph.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationSingleton {
    public static final Logger LOG = LoggerFactory.getLogger(ConfigurationSingleton.class);

    private static final String CONFIG_NAME = "config.properties";

    private static final String PORT_NAME = "port";

    private static final String DIRECTORY_NAME = "directory";

    private static final String CACHE_URI_NAME = "cache.uri";

    private static final String DATABASE_NODE = "db.node";

    private static final String DATABASE_PORT = "db.port";

    private int port;

    private String directory;

    private String cacheUri;

    private String databaseNode;

    private int databasePort;

    private ConfigurationSingleton() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }

        port = Integer.parseInt(properties.getProperty(PORT_NAME));
        directory = properties.getProperty(DIRECTORY_NAME);
        cacheUri = properties.getProperty(CACHE_URI_NAME);
        databaseNode = properties.getProperty(DATABASE_NODE);
        databasePort = Integer.parseInt(properties.getProperty(DATABASE_PORT));
    }

    private static class SingletonHelper {
        private static final ConfigurationSingleton INSTANCE = new ConfigurationSingleton();
    }

    public static ConfigurationSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public int getPort() {
        return port;
    }

    public String getDirectory() {
        return directory;
    }

    public String getCacheUri() {
        return cacheUri;
    }

    public String getDatabaseNode() {
        return databaseNode;
    }

    public int getDatabasePort() {
        return databasePort;
    }
}
