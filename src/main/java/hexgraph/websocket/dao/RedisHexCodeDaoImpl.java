package hexgraph.websocket.dao;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

import java.time.LocalDateTime;

public class RedisHexCodeDaoImpl implements HexCodeDao {
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
        asyncCommands.get(imagePath);
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
