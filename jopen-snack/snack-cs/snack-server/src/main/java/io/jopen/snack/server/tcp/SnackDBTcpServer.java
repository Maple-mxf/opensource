package io.jopen.snack.server.tcp;

import io.jopen.snack.common.protol.RpcData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.io.IOException;

/**
 * 处理客户端的请求
 *
 * @author maxuefeng
 * @since 2019/10/26
 */
public class SnackDBTcpServer {

    private static ClientIntentionParser clientIntentionParser = new ClientIntentionParser();

    public static void main(String[] args) {

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
        protected void channelRead0(ChannelHandlerContext ctx, RpcData.C2S data) throws IOException {
            // 解析数据  并且进行执行得到结果 写入到流中
            RpcData.S2C response = clientIntentionParser.parse(data);
            ctx.channel().writeAndFlush(response);
        }
    }
}
