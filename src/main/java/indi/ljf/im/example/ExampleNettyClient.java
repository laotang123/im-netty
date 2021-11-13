package indi.ljf.im.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * @author: ljf
 * @date: 2021/10/22 11:15
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@Slf4j
public class ExampleNettyClient {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                });
        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();
        log.info("客户端连接：{}", channel);
        while (true) {
            Scanner sc = new Scanner(System.in);
            String content = sc.nextLine();
            if ("quit".equals(content)) {
                break;
            }
            try {
                channel.writeAndFlush(content);
                log.info("发送数据：{}", content);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        channel.close();
    }
}
