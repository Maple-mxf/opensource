package io.jopen.snack.server.netty;

import io.jopen.snack.common.IntermediateExpression;
import io.jopen.snack.common.serialize.KryoHelper;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * <p>{@link io.netty.bootstrap.ServerBootstrap}</p>
 * <p>{@link io.jopen.snack.common.serialize.KryoHelper#deserialization(byte[], Class)}</p>
 * <p>{@link io.jopen.snack.common.serialize.KryoHelper#serialization(Object)}</p>
 *
 * @author maxuefeng
 * @since 2019/10/26
 */
public class NettyServer {

    private int port;

    @Before
    public void before() {
        port = 4052;
    }

    private void bind() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024); // 连接数
            bootstrap.option(ChannelOption.TCP_NODELAY, true); // 不延迟，消息立即发送
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); // 长连接
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline p = socketChannel.pipeline();
                    // 添加NettyServerHandler，用来处理Server端接收和处理消息的逻辑
                    p.addLast(new ServerChannelHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            if (channelFuture.isSuccess()) {
                System.err.println("启动Netty服务成功，端口号：" + this.port);
            }
            // 关闭连接
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            System.err.println("启动Netty服务异常，异常信息：" + e.getMessage());
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    /**
     * <p>{@link ChannelHandler}</p>
     * <p>{@link ChannelHandlerAdapter#handlerAdded(ChannelHandlerContext)}</p>
     * <p>{@link ChannelHandlerAdapter}</p>
     */
    class ServerChannelHandler extends ChannelInboundHandlerAdapter {
        /**
         * @param ctx
         * @param msg
         * @throws Exception
         * @see ByteBuf
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
            ByteBuf buf = (ByteBuf) msg;

            byte[] bytes = new byte[buf.readableBytes()];
            byte[] array = buf.array();
            System.err.println(array.length);


            IntermediateExpression<Map> expression = KryoHelper.deserialization(bytes, IntermediateExpression.class);
            System.err.println(expression);
        }
    }

    @Test
    public void startServer() {
        bind();
    }
}
