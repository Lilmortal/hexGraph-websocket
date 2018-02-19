package hexgraph.websocket.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexgraph.websocket.cache.Cache;
import hexgraph.websocket.cache.RedisCache;
import hexgraph.websocket.config.Configuration;
import hexgraph.websocket.config.ConfigurationImpl;
import hexgraph.websocket.dao.CassandraHexCodeDaoImpl;
import hexgraph.websocket.dao.DbConnectDataSource;
import hexgraph.websocket.dao.DbDataSource;
import hexgraph.websocket.dao.HexCodeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class HexCodeServiceImpl implements HexCodeService {
    public static final Logger LOGGER = LoggerFactory.getLogger(HexCodeServiceImpl.class);

    private Configuration configuration = new ConfigurationImpl();

    private Cache hexCodeCache = new RedisCache();

    private HexCodeDao hexCodeDao = new CassandraHexCodeDaoImpl();

    @Override
    public void connect(Configuration configuration) {
        hexCodeCache.connect(configuration.getCacheUri());

        DbConnectDataSource dbConnectDataSource = DbConnectDataSource.builder(configuration.getDatabaseNode()).port(configuration.getDatabasePort()).createDataSource();
        hexCodeDao.connect(dbConnectDataSource);

        DbDataSource dbDataSource = DbDataSource.builder(configuration.getDatabaseName())
                .replicationStrategy(configuration.getDatabaseReplicationStrategy())
                .replicationFactor(configuration.getDatabaseReplicationFactor())
                .createDbDataSource();

        this.createDatabase(dbDataSource);
        this.createHexCodeTable(dbDataSource);
    }

    @Override
    public void createDatabase(DbDataSource dbDataSource) {
        hexCodeDao.createDatabase(dbDataSource);
    }

    @Override
    public void createHexCodeTable(DbDataSource dbDataSource) {
        hexCodeDao.createHexCodeTable(dbDataSource);
    }

    @Override
    public void getImageHexCode(String imagePath) {
        hexCodeDao.getImageHexCode(imagePath);
    }

    @Override
    public void setImageHexCode(String imagePath, String json) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(json);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDateTime creationDate = LocalDateTime.parse(jsonNode.get("creationDate").asText(), formatter);
            String counts = jsonNode.get("counts").asText();
            this.setImageHexCode(imagePath, creationDate, counts);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void setImageHexCode(String imagePath, LocalDateTime creationDate, Map<String, String> counts) {
        StringBuilder countsBuilder = new StringBuilder();

        counts.forEach((k,v) -> countsBuilder.append("{" + k + ": " + v + "}"));

        LOGGER.info(countsBuilder.toString());
        this.setImageHexCode(imagePath, creationDate, countsBuilder.toString());
    }

    @Override
    public void setImageHexCode(String imagePath, LocalDateTime creationDate, String counts) {
        hexCodeDao.insertImageHexCode(imagePath, creationDate, counts);
    }
}
