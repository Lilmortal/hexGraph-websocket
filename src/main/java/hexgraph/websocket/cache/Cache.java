package hexgraph.websocket.cache;

import io.lettuce.core.RedisFuture;

public interface Cache {
    void connect(String uri);

    void set(String key, String value);

    RedisFuture<String> get(String key);
}
