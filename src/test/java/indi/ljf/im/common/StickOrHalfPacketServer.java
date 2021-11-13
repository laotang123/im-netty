package indi.ljf.im.common;

import indi.ljf.im.example.handler.FirstServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @author: ljf
 * @date: 2021/10/24 17:18
 * @description: 粘包和半包测试
 * @modified By:
 * @version: $ 1.0
 */
public class StickOrHalfPacketServer {
    public static void main(String[] args) {

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(Integer.MAX_VALUE));
                        ch.pipeline().addLast(new FirstServerHandler());
                    }
                }).bind(8081);
    }

}
