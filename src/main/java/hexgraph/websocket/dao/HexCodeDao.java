package hexgraph.websocket.dao;

import java.time.LocalDateTime;
import java.util.Map;

public interface HexCodeDao {
    void connect(DbConnectDataSource dbConnectDataSource);

    void createDatabase(DbDataSource dbDataSource);

    void createHexCodeTable(DbDataSource dbDataSource);

    void getImageHexCode(String imagePath);

    void insertImageHexCode(String imagePath, LocalDateTime creationDate, String counts);

    void close();
}
