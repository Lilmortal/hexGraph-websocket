package hexgraph.websocket.cache;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

import java.util.concurrent.Future;

public class RedisCache implements Cache {
    private RedisAsyncCommands<String, String> asyncCommands;

    @Override
    public void connect(String uri) {
        RedisClient redisClient = RedisClient
                .create(uri);
        StatefulRedisConnection<String, String> connection
                = redisClient.connect();

        asyncCommands = connection.async();
    }

    @Override
    public void set(String key, String value) {
        asyncCommands.set(key, value);
    }

    @Override
    public Future<String> get(String key) {
        return asyncCommands.get(key);
    }
}
