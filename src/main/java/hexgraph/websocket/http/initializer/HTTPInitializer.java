package hexgraph.websocket.http.initializer;

import hexgraph.websocket.cache.Cache;
import hexgraph.websocket.cache.RedisCache;
import hexgraph.websocket.config.Configuration;
import hexgraph.websocket.config.ConfigurationImpl;
import hexgraph.websocket.http.handler.HttpServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


public class HTTPInitializer extends ChannelInitializer<SocketChannel> {
    private static final String HTTP_SERVER_CODEC_NAME = "httpServerCodec";

    private static final String HTTP_HANDLER_NAME = "httpHandler";

    private Cache cache = null;

    private static final Configuration CONFIGURATION = new ConfigurationImpl();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // TODO: Should redis have connection pool for multiple concurrent connections?
        cache = new RedisCache();
        cache.connect(CONFIGURATION.getCacheUri());

        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(HTTP_SERVER_CODEC_NAME, new HttpServerCodec());
        pipeline.addLast(HTTP_HANDLER_NAME, new HttpServerHandler(cache));
    }


}
