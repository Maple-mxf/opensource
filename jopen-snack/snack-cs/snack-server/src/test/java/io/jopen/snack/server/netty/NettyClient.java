package io.jopen.snack.server.netty;

import io.jopen.snack.common.IntermediateExpression;
import io.jopen.snack.common.serialize.KryoHelper;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * {@link NettyServer}
 *
 * @author maxuefeng
 * @since 2019/10/26
 */
public class NettyClient {

    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private String host = "127.0.0.1";
    private int port = 4052;


    private void start() throws InterruptedException {

        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.group(eventLoopGroup);
            bootstrap.remoteAddress(host, port);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    socketChannel.pipeline().addLast(new ClientChannelHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            if (channelFuture.isSuccess()) {
                System.err.println("连接服务器成功");
            }
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    /**
     * ChannelHandlerAdapter
     * {@link ChannelHandlerAdapter }
     *
     * @see ChannelInboundHandlerAdapter
     */
    class ClientChannelHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

            IntermediateExpression<Map> expression = IntermediateExpression.buildFor(Map.class);
            byte[] data = KryoHelper.serialization(expression);
            ByteBuf buffer = Unpooled.buffer();
            buffer.writeBytes(data);
            ctx.writeAndFlush(buffer);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf buf = (ByteBuf) msg;
            String rev = getMessage(buf);
            System.err.println("客户端收到服务器消息:" + rev);
        }

        private String getMessage(ByteBuf buf) {
            byte[] con = new byte[buf.readableBytes()];
            buf.readBytes(con);
            return new String(con, StandardCharsets.UTF_8);
        }
    }

    @Test
    public void startClient() throws InterruptedException {
        start();
    }
}
