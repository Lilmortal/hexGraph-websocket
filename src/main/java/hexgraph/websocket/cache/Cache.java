package hexgraph.websocket.cache;

import java.util.concurrent.Future;

public interface Cache {
    void connect(String uri);

    void set(String key, String value);

    Future<String> get(String key);
}
