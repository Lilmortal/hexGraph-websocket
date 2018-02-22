package hexgraph.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationSingleton {
    public static final Logger LOG = LoggerFactory.getLogger(ConfigurationSingleton.class);

    private static final String CONFIG_NAME = "config.properties";

    private static final String DIRECTORY_NAME = "directory";

    private static final String CHANNEL_PORT = "channel.port";

    private static final String CACHE_NODE = "cache.node";

    private static final String CACHE_PORT = "cache.port";

    private static final String DATABASE_NODE = "db.node";

    private static final String DATABASE_PORT = "db.port";

    private static final String DATABASE_NAME = "db.name";

    private static final String DATABASE_REPLICATION_STRATEGY = "db.replication.strategy";

    private static final String DATABASE_REPLICATION_FACTOR = "db.replication.factor";

    private String directory;

    private Integer channelPort;

    private String cacheNode;

    private Integer cachePort;

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

        directory = properties.getProperty(DIRECTORY_NAME);
        channelPort = properties.getProperty(CHANNEL_PORT) != null ? Integer.parseInt(properties.getProperty(CHANNEL_PORT)) : null;
        cacheNode = properties.getProperty(CACHE_NODE);
        cachePort = properties.getProperty(CACHE_PORT) != null ? Integer.parseInt(properties.getProperty(CACHE_PORT)) : null;
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

    public String getDirectory() {
        return directory;
    }

    public Integer getChannelPort() {
        return channelPort;
    }

    public String getCacheNode() {
        return cacheNode;
    }

    public Integer getCachePort() {
        return cachePort;
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
