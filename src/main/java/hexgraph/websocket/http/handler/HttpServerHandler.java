package hexgraph.websocket.http.handler;

import hexgraph.websocket.cache.Cache;
import hexgraph.websocket.services.HexCodeService;
import hexgraph.websocket.websocket.handler.InboundWebSocketHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    public static final Logger LOGGER = LoggerFactory.getLogger(HttpServerHandler.class);

    private static final String WEB_SOCKET_HANDLER_NAME = "webSocketHandler";

    private HexCodeService hexCodeService = null;

    public HttpServerHandler(HexCodeService hexCodeService) {
        this.hexCodeService = hexCodeService;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;

            LOGGER.info("Http Request Received");

            HttpHeaders headers = httpRequest.headers();
            LOGGER.info("Connection : " + headers.get("Connection"));
            LOGGER.info("Upgrade : " + headers.get("Upgrade"));

            if (headers.get("Connection").equalsIgnoreCase("Upgrade") ||
                    headers.get("Upgrade").equalsIgnoreCase("WebSocket")) {

                //Adding new handler to the existing pipeline to handle WebSocket Messages
                ctx.pipeline().replace(this, WEB_SOCKET_HANDLER_NAME, new InboundWebSocketHandler(hexCodeService));

                LOGGER.info("InboundWebSocketHandler added to the pipeline");

                LOGGER.info("Opened Channel : " + ctx.channel());

                LOGGER.info("Handshaking....");
                //Do the Handshake to upgrade connection from HTTP to WebSocket protocol
                handleHandshake(ctx, httpRequest);
                LOGGER.info("Handshake is done");

            }
        } else {
            LOGGER.info("Incoming request is unknown");
        }
    }

    /* Do the handshaking for WebSocket request */
    protected void handleHandshake(ChannelHandlerContext ctx, HttpRequest req) throws URISyntaxException {
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketURL(req),
                null, true);
        WebSocketServerHandshaker handShaker = wsFactory.newHandshaker(req);
        if (handShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handShaker.handshake(ctx.channel(), req);
        }
    }


    protected String getWebSocketURL(HttpRequest req) {
        LOGGER.info("Req URI : " + req.uri());
        String url = "ws://" + req.headers().get("Host") + req.uri();
        LOGGER.info("Constructed URL : " + url);
        return url;
    }
}
