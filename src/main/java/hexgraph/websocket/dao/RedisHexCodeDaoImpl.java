package hexgraph.websocket.dao;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

public class RedisHexCodeDaoImpl implements HexCodeDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(RedisHexCodeDaoImpl.class);

    private StatefulRedisConnection<String, String> connection;

    private RedisAsyncCommands<String, String> asyncCommands;

    @Override
    public void connect(DbConnectDataSource dbConnectDataSource) {
        RedisClient redisClient = RedisClient
                .create("redis://" + dbConnectDataSource.getNode() + ":" + dbConnectDataSource.getPort());
        connection = redisClient.connect();
        asyncCommands = connection.async();
    }

    @Override
    public void createDatabase(DbDataSource dbDataSource) {
        // Redis does not have the concept of databases but can be used as a key value store
    }

    @Override
    public void createHexCodeTable(DbDataSource dbDataSource) {
        // Redis does not have the concept of tables but can be used as a key value store
    }

    @Override
    public void getImageHexCode(String imagePath) {

        RedisFuture<String> result = asyncCommands.get(imagePath);
        try {
            LOGGER.info(result.get().toString());
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void insertImageHexCode(String imagePath, LocalDateTime creationDate, String counts) {
        asyncCommands.set(imagePath, "{ creationDate: " + creationDate.toString() + ", counts: " + counts);
    }

    @Override
    public void close() {
        connection.close();
    }
}
