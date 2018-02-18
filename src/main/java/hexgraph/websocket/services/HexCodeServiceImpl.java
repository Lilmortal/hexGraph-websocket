package hexgraph.websocket.services;

import hexgraph.websocket.cache.Cache;
import hexgraph.websocket.cache.RedisCache;
import hexgraph.websocket.config.Configuration;
import hexgraph.websocket.config.ConfigurationImpl;
import hexgraph.websocket.dao.CassandraHexCodeDaoImpl;
import hexgraph.websocket.dao.DbConnectDataSource;
import hexgraph.websocket.dao.HexCodeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
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
    }

    @Override
    public void getImageHexCode(String imagePath) {
        hexCodeDao.getImageHexCode(imagePath);
    }

    @Override
    public void setImageHexCode(String imagePath, String json) {
        // TODO:
        //this.setImageHexCode(imagePath);
    }

    @Override
    public void setImageHexCode(String imagePath, LocalDateTime creationDate, Map<String, String> counts) {
        StringBuilder countsBuilder = new StringBuilder();

        counts.forEach((k,v) -> countsBuilder.append("{" + k + ": " + v + "}"));

        LOGGER.info(countsBuilder.toString());
        hexCodeDao.insertImageHexCode(imagePath, creationDate, countsBuilder.toString());
    }
}
