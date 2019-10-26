package io.jopen.snack.server.snetty;

import com.google.protobuf.Any;
import io.jopen.snack.common.Condition;
import io.jopen.snack.common.protol.SerializationDataInfo;
import io.jopen.snack.common.serialize.KryoHelper;
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
import org.junit.Test;

import java.util.List;

/**
 * @author maxuefeng
 * @since 2019/10/26
 */
public class Server {

    @Test
    public void startServer() {
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


    class ProtoServerInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();

            //解码器，通过Google Protocol Buffers序列化框架动态的切割接收到的ByteBuf
            pipeline.addLast(new ProtobufVarint32FrameDecoder());

            //服务器端接收的是客户端对象，所以这边将接收对象进行解码生产实列
            pipeline.addLast(new ProtobufDecoder(SerializationDataInfo.RpcExpression.getDefaultInstance()));

            //Google Protocol Buffers编码器
            pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());

            //Google Protocol Buffers编码器
            pipeline.addLast(new ProtobufEncoder());

            pipeline.addLast(new ProtoServerHandler());
        }
    }

    class ProtoServerHandler extends SimpleChannelInboundHandler<SerializationDataInfo.RpcExpression> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, SerializationDataInfo.RpcExpression msg) {
            List<Any> conditionsList = msg.getConditionsList();
            for (Any any : conditionsList) {
                try {
                    Condition condition = KryoHelper.deserialization(any.getValue().toByteArray(), Condition.class);
                    System.err.println(condition.test(null));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            ctx.channel().writeAndFlush(msg);
        }
    }
}
