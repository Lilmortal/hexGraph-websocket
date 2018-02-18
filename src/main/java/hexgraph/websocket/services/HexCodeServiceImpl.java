package hexgraph.websocket.services;

import hexgraph.websocket.cache.Cache;
import hexgraph.websocket.cache.RedisCache;
import hexgraph.websocket.config.Configuration;
import hexgraph.websocket.config.ConfigurationImpl;
import hexgraph.websocket.dao.CassandraHexCodeDaoImpl;
import hexgraph.websocket.dao.DbConnectDataSource;
import hexgraph.websocket.dao.HexCodeDao;

import java.time.LocalDateTime;
import java.util.Map;

public class HexCodeServiceImpl implements HexCodeService {
    private Configuration configuration = new ConfigurationImpl();

    private Cache hexCodeCache = new RedisCache();

    private HexCodeDao hexCodeDao = new CassandraHexCodeDaoImpl();

    @Override
    public void connect(Configuration configuration) {
        DbConnectDataSource dbConnectDataSource = DbConnectDataSource.builder(configuration.getDatabaseNode()).port(configuration.getDatabasePort()).createDataSource();
        hexCodeDao.connect(dbConnectDataSource);
    }

    @Override
    public void getImageHexCode(String imagePath) {
        hexCodeDao.getImageHexCode(imagePath);
    }

    @Override
    public void setImageHexCode(String imagePath, LocalDateTime creationDate, Map<String, String> counts) {
        hexCodeDao.insertImageHexCode(imagePath, creationDate, counts);
    }
}
