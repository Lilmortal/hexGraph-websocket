package hexgraph.websocket.websocket.handler;

import hexgraph.websocket.config.Configuration;
import hexgraph.websocket.config.ConfigurationImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.*;
import java.util.Timer;
import java.util.TimerTask;

public class InboundWebSocketHandler extends ChannelInboundHandlerAdapter {
    public static final Logger LOGGER = LoggerFactory.getLogger(InboundWebSocketHandler.class);

    private static final Configuration CONFIGURATION = new ConfigurationImpl();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof WebSocketFrame) {
            if (msg instanceof TextWebSocketFrame) {
                LOGGER.info("TextWebSocketFrame Received : ");

                try {
                    Path directoryPath = Paths.get(CONFIGURATION.getDirectory());
                    WatchService watchService = FileSystems.getDefault().newWatchService();
                    directoryPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

                    boolean valid = true;
                    do {
                        WatchKey watchKey = watchService.take();

                        for (WatchEvent event : watchKey.pollEvents()) {
//                            WatchEvent.Kind kind = event.kind();
                            if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
                                StringBuilder contents = new StringBuilder();
                                String fileName = event.context().toString();
                                Files.lines(new File(CONFIGURATION.getDirectory() + fileName).toPath())
                                        .map(s -> s.trim())
                                        .filter(s -> !s.isEmpty())
                                        .forEach(content -> contents.append(content));

                                ctx.channel().write(new TextWebSocketFrame(contents.toString()));
                                ctx.flush();
                            }
                        }
                        valid = watchKey.reset();

                    } while (valid);
                } catch (NoSuchFileException e) {
                    LOGGER.error(e.getMessage());
                }
            } else {
                LOGGER.error("Unsupported WebSocketFrame");
            }
        }
    }
}
