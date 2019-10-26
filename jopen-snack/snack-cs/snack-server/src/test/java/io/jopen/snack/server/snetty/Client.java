package io.jopen.snack.server.snetty;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import io.jopen.snack.common.Condition;
import io.jopen.snack.common.protol.SerializationDataInfo;
import io.jopen.snack.common.serialize.KryoHelper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

/**
 * @author maxuefeng
 * @since 2019/10/26
 */
public class Client {

    @Test
    public void startClient() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new ProtoClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    class ProtoClientInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) {
            ChannelPipeline pipeline = ch.pipeline();

            //解码器，通过Google Protocol Buffers序列化框架动态的切割接收到的ByteBuf
            pipeline.addLast(new ProtobufVarint32FrameDecoder());

            //将接收到的二进制文件解码成具体的实例，这边接收到的是服务端的ResponseBank对象实列
            pipeline.addLast(new ProtobufDecoder(SerializationDataInfo.RpcExpression.getDefaultInstance()));

            //Google Protocol Buffers编码器
            pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());

            //Google Protocol Buffers编码器
            pipeline.addLast(new ProtobufEncoder());

            pipeline.addLast(new ProtoClientHandler());
        }
    }

    class ProtoClientHandler extends SimpleChannelInboundHandler<SerializationDataInfo.RpcExpression> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, SerializationDataInfo.RpcExpression msg) {
            System.out.println(msg);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.channel().writeAndFlush(Client.this.testBuildCondition());
        }
    }

    // 将函数式接口序列化
    private Condition condition = (Condition) Objects::nonNull;

    /**
     * @throws IOException
     * @see java.lang.invoke.SerializedLambda
     * @see com.google.protobuf.Api
     */
    public SerializationDataInfo.RpcExpression testBuildCondition() throws IOException {

        // 创建expression实体类
        Any any = Any.newBuilder().setValue(ByteString.copyFrom(KryoHelper.serialization(condition))).build();
        return SerializationDataInfo.RpcExpression.newBuilder()
                .addConditions(any)
                .build();

    }
}
