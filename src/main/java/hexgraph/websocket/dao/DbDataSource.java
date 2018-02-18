package hexgraph.websocket.dao;

public class DbDataSource {
    private final String name;

    private String replicationStrategy;

    private Integer replicationFactor;

    private DbDataSource(String name, String replicationStrategy, Integer replicationFactor) {
        this.name = name;
        this.replicationStrategy = replicationStrategy;
        this.replicationFactor = replicationFactor;
    }

    public String getName() {
        return name;
    }

    public String getReplicationStrategy() {
        return replicationStrategy;
    }

    public Integer getReplicationFactor() {
        return replicationFactor;
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public static class Builder {
        private final String name;

        private String replicationStrategy;

        private Integer replicationFactor;

        public Builder(String name) {
            this.name = name;
        }

        public Builder replicationStrategy(String replicationStrategy) {
            this.replicationStrategy = replicationStrategy;
            return this;
        }

        public Builder replicationFactor(Integer replicationFactor) {
            this.replicationFactor = replicationFactor;
            return this;
        }

        public DbDataSource createDbDataSource() {
            return new DbDataSource(name, replicationStrategy, replicationFactor);
        }
    }
}
