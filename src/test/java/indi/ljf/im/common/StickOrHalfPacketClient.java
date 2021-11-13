package indi.ljf.im.common;

import indi.ljf.im.example.handler.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author: ljf
 * @date: 2021/10/24 17:18
 * @description: 粘包和半包测试
 * @modified By:
 * @version: $ 1.0
 */
public class StickOrHalfPacketClient {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                }).connect("127.0.0.1", 8081);
    }
}
