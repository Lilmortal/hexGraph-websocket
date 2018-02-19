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

    private static final String DATABASE_NAME = "db.name";

    private static final String DATABASE_REPLICATION_STRATEGY = "db.replication.strategy";

    private static final String DATABASE_REPLICATION_FACTOR = "db.replication.factor";

    private Integer port;

    private String directory;

    private String cacheUri;

    private String databaseNode;

    private Integer databasePort;

    private String databaseName;

    private String databaseReplicationStrategy;

    private Integer databaseReplicationFactor;

    private ConfigurationSingleton() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }

        port = properties.getProperty(PORT_NAME) != null ? Integer.parseInt(properties.getProperty(PORT_NAME)) : null;
        directory = properties.getProperty(DIRECTORY_NAME);
        cacheUri = properties.getProperty(CACHE_URI_NAME);
        databaseNode = properties.getProperty(DATABASE_NODE);
        databasePort = properties.getProperty(DATABASE_PORT) != null ? Integer.parseInt(properties.getProperty(DATABASE_PORT)) : null;
        databaseName = properties.getProperty(DATABASE_NAME);
        databaseReplicationStrategy = properties.getProperty(DATABASE_REPLICATION_STRATEGY);
        databaseReplicationFactor = properties.getProperty(DATABASE_REPLICATION_FACTOR) != null ? Integer.parseInt(properties.getProperty(DATABASE_REPLICATION_FACTOR)) : null;
    }

    private static class SingletonHelper {
        private static final ConfigurationSingleton INSTANCE = new ConfigurationSingleton();
    }

    public static ConfigurationSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public Integer getPort() {
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

    public Integer getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseReplicationStrategy() {
        return databaseReplicationStrategy;
    }

    public Integer getDatabaseReplicationFactor() {
        return databaseReplicationFactor;
    }
}
