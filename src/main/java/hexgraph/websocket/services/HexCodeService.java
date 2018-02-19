package hexgraph.websocket.services;

import hexgraph.websocket.config.Configuration;
import hexgraph.websocket.dao.DbDataSource;

import java.time.LocalDateTime;
import java.util.Map;

public interface HexCodeService {
    void connect(Configuration configuration);

    void createDatabase(DbDataSource dbDataSource);

    void createHexCodeTable(DbDataSource dbDataSource);

    void getImageHexCode(String imagePath);

    void setImageHexCode(String imagePath, String json);

    void setImageHexCode(String imagePath, LocalDateTime creationDate, Map<String, String> counts);

    void setImageHexCode(String imagePath, LocalDateTime creationDate, String counts);
}
