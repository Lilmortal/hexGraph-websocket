package hexgraph.websocket.http.initializer;

import hexgraph.websocket.http.handler.HttpServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


public class HTTPInitializer extends ChannelInitializer<SocketChannel> {
    private static final String HTTP_SERVER_CODEC_NAME = "httpServerCodec";

    private static final String HTTP_HANDLER_NAME = "httpHandler";

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(HTTP_SERVER_CODEC_NAME, new HttpServerCodec());
        pipeline.addLast(HTTP_HANDLER_NAME, new HttpServerHandler());
    }
}
