package io.jopen.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author maxuefeng
 * @since 2019/12/14
 */
public class JOpenNettyServer {

    public static void main(String[] args) {

        // 接受请求
        EventLoopGroup workGroup = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        //
        ServerBootstrap bootstrap = new ServerBootstrap();

        //
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new InitChannel());
    }

    static class InitChannel extends ChannelInitializer<SocketChannel> {

        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast();
        }
    }
}
