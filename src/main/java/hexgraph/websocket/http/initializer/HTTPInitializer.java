package hexgraph.websocket.http.initializer;

import hexgraph.websocket.cache.Cache;
import hexgraph.websocket.cache.RedisCache;
import hexgraph.websocket.config.Configuration;
import hexgraph.websocket.config.ConfigurationImpl;
import hexgraph.websocket.dao.DbDataSource;
import hexgraph.websocket.http.handler.HttpServerHandler;
import hexgraph.websocket.services.HexCodeService;
import hexgraph.websocket.services.HexCodeServiceImpl;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


public class HTTPInitializer extends ChannelInitializer<SocketChannel> {
    private static final String HTTP_SERVER_CODEC_NAME = "httpServerCodec";

    private static final String HTTP_HANDLER_NAME = "httpHandler";

    private static final Configuration CONFIGURATION = new ConfigurationImpl();

    private final HexCodeService hexCodeService = new HexCodeServiceImpl();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // TODO: Should redis have connection pool for multiple concurrent connections?
        hexCodeService.connect(CONFIGURATION);

        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(HTTP_SERVER_CODEC_NAME, new HttpServerCodec());
        pipeline.addLast(HTTP_HANDLER_NAME, new HttpServerHandler(hexCodeService));
    }


}
