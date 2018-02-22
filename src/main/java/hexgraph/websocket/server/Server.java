package hexgraph.websocket.server;

import hexgraph.websocket.config.Configuration;
import hexgraph.websocket.config.ConfigurationImpl;
import hexgraph.websocket.http.initializer.HTTPInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
    public static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private static final int NUM_OF_BOSS_GROUP_THREADS = 1;

    private static final int SO_BACKLOG_VALUE = 1024;

    public void start() {
        Configuration configuration = new ConfigurationImpl();

        EventLoopGroup bossGroup = new NioEventLoopGroup(NUM_OF_BOSS_GROUP_THREADS);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.option(ChannelOption.SO_BACKLOG, SO_BACKLOG_VALUE);
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HTTPInitializer());

            Channel channel = serverBootstrap.bind(configuration.getChannelPort()).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
