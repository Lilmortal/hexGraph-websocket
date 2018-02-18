package hexgraph.websocket.dao;

public class DbConnectDataSource {
    private final String node;

    private final Integer port;

    private DbConnectDataSource(String node, Integer port) {
        this.node = node;
        this.port = port;
    }

    public String getNode() {
        return node;
    }

    public Integer getPort() {
        return port;
    }

    public static Builder builder(String node) {
        return new Builder(node);
    }

    public static class Builder {
        private final String node;

        private Integer port;

        public Builder(String node) {
            this.node = node;
        }

        public Builder port(Integer port) {
            this.port = port;
            return this;
        }

        public DbConnectDataSource createDataSource() {
            return new DbConnectDataSource(node, port);
        }
    }
}
