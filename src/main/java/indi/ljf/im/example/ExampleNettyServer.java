package indi.ljf.im.example;

import indi.ljf.im.example.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: ljf
 * @date: 2021/10/22 11:03
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@Slf4j
public class ExampleNettyServer {
    public static void main(String[] args) {
        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        serverBootstrap.group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInBoundHandlerA());
                        ch.pipeline().addLast(new ChannelInBoundHandlerB());
                        ch.pipeline().addLast(new ChannelInBoundHandlerC());

                        ch.pipeline().addLast(new ChannelOutBoundHandlerA());
                        ch.pipeline().addLast(new ChannelOutBoundHandlerB());
                        ch.pipeline().addLast(new ChannelOutBoundHandlerC());
                    }
                });
        bind(serverBootstrap, 8000);
    }

    /**
     * 自动递增绑定端口
     */
    public static void bind(final ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("绑定端口：{}成功", port);
            } else {
                log.info("绑定端口 {}失败，尝试端口：{}", port, port + 1);
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
