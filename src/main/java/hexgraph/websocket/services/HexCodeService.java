package hexgraph.websocket.services;

import hexgraph.websocket.config.Configuration;

import java.time.LocalDateTime;
import java.util.Map;

public interface HexCodeService {
    void connect(Configuration configuration);

    void getImageHexCode(String imagePath);

    void setImageHexCode(String imagePath, LocalDateTime creationDate, Map<String, String> counts);
}
