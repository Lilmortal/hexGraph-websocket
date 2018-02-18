package hexgraph.websocket.dao;

import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Map;

import static com.datastax.driver.core.Cluster.*;

public class CassandraHexCodeDaoImpl implements HexCodeDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(CassandraHexCodeDaoImpl.class);

    private Cluster cluster;

    private Session session;

    @Override
    public void connect(DbConnectDataSource dbConnectDataSource) {
        Builder builder = Cluster.builder().addContactPoint(dbConnectDataSource.getNode());

        Integer port = dbConnectDataSource.getPort();
        if (port != null) {
            builder.withPort(port);
        }

        cluster = builder.build();
        session = cluster.connect();
    }

    @Override
    public void createDatabase(DbDataSource dbDataSource) {
        session.execute(String.format(
                "CREATE KEYSPACE IF NOT EXISTS %s" +
                        "WITH replication = {'class': '%s', 'replication_factor':%d};"
                , dbDataSource.getName(), dbDataSource.getReplicationStrategy(), dbDataSource.getReplicationFactor()));
    }

    @Override
    public void createHexCodeTable() {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS " + TableList.HEX_CODE_TABLE + "(");
        sb.append("imagePath text PRIMARY KEY,");
        sb.append("creationDate date,");
        sb.append("counts timestamp");
        session.execute(sb.toString());
    }

    @Override
    public void getImageHexCode(String imagePath) {
        PreparedStatement statement = session.prepare(String.format("SELECT * FROM %s WHERE imagePath = ?;", TableList.HEX_CODE_TABLE));

        BoundStatement boundStatement = new BoundStatement(statement);
        ResultSet resultSet = session.execute(boundStatement.bind(imagePath));
        LOGGER.info(resultSet.toString());
    }

    @Override
    public void insertImageHexCode(String imagePath, LocalDateTime creationDate, Map<String, String> counts) {
        PreparedStatement statement = session.prepare(String.format("INSERT INTO %s (imagePath, creationDate, counts) VALUES (?, ?, ?);"));

        BoundStatement boundStatement = new BoundStatement(statement);
        ResultSet resultSet = session.execute(boundStatement.bind(imagePath, creationDate, counts));
        LOGGER.info(resultSet.toString());
    }

    @Override
    public void close() {
        session.close();
        cluster.close();
    }
}
