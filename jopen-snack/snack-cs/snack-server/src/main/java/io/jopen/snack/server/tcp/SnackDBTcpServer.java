package io.jopen.snack.server.tcp;

import io.jopen.snack.common.SnackEventSource;
import io.jopen.snack.common.listener.db.CreateDatabaseListener;
import io.jopen.snack.common.listener.db.DropDatabaseListener;
import io.jopen.snack.common.listener.row.DeleteRowListener;
import io.jopen.snack.common.listener.row.InsertRowListener;
import io.jopen.snack.common.listener.row.UpdateRowListener;
import io.jopen.snack.common.listener.table.CreateTableEventListener;
import io.jopen.snack.common.listener.table.DropTableListener;
import io.jopen.snack.common.listener.table.ModifyTableListener;
import io.jopen.snack.common.protol.RpcData;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 处理客户端的请求
 *
 * @author maxuefeng
 * @since 2019/10/26
 */
public class SnackDBTcpServer {

    private static ClientIntentionParser clientIntentionParser = new ClientIntentionParser();

    public static void main(String[] args) {
        //
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup wokerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, wokerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ProtoServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
    }

    static class ProtoServerInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();

            // 解码器，通过Google Protocol Buffers序列化框架动态的切割接收到的ByteBuf
            pipeline.addLast(new ProtobufVarint32FrameDecoder());

            // 服务器端接收的是客户端对象，所以这边将接收对象进行解码生产实列
            pipeline.addLast(new ProtobufDecoder(RpcData.C2S.getDefaultInstance()));

            // Google Protocol Buffers编码器
            pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());

            // Google Protocol Buffers编码器
            pipeline.addLast(new ProtobufEncoder());

            // 添加数据处理器
            pipeline.addLast(new ProtoServerHandler());
        }
    }

    static class ProtoServerHandler extends SimpleChannelInboundHandler<RpcData.C2S> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, RpcData.C2S data) throws Exception {
            // 解析数据  并且进行执行得到结果 写入到流中
            RpcData.S2C response = clientIntentionParser.parse(data);
            ctx.channel().writeAndFlush(response);
        }
    }

    private final static SnackEventSource eventSource = new SnackEventSource();

    static {
        // 表格监听器
        eventSource.registerListener(new CreateTableEventListener());
        eventSource.registerListener(new DropTableListener());
        eventSource.registerListener(new ModifyTableListener());


        // 数据库监听器
        eventSource.registerListener(new CreateDatabaseListener());
        eventSource.registerListener(new DropDatabaseListener());

        // row元数据监听器
        eventSource.registerListener(new UpdateRowListener());
        eventSource.registerListener(new InsertRowListener());
        eventSource.registerListener(new DeleteRowListener());
    }
}
