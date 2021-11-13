package indi.ljf.im.client;

import indi.ljf.im.client.handler.*;
import indi.ljf.im.common.codec.CheckProtocolFrameDecoder;
import indi.ljf.im.common.codec.PacketDecoder;
import indi.ljf.im.common.codec.PacketEncoder;
import indi.ljf.im.protocol.command.ConsoleCommandManager;
import indi.ljf.im.protocol.request.MessageRequestPacket;
import indi.ljf.im.utils.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author: ljf
 * @date: 2021/10/23 9:37
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@Slf4j
public class NettyClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //日志打印
                        ch.pipeline().addLast("LoggingHandler", new LoggingHandler(LogLevel.DEBUG));
                        //校验魔数和粘包半包
                        ch.pipeline().addLast("CheckProtocolFrameDecoder", new CheckProtocolFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        //解码
                        ch.pipeline().addLast("PacketDecoder", new PacketDecoder());
                        //指令处理
                        ch.pipeline().addLast("CommandResponseHandler", new CommandResponseHandler());
                        //用户对用户聊天
                        ch.pipeline().addLast("SingleChatResponseHandler", new SingleChatResponseHandler());
                        //处理开始群聊指令
                        ch.pipeline().addLast("GroupChatResponseHandler", new GroupChatResponseHandler());
                        //消息编码为二进制
                        ch.pipeline().addLast("PacketEncoder", new PacketEncoder());
                    }
                });

        connect(bootstrap, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, int retry) {
        bootstrap.connect(HOST, PORT).addListener(future -> {
            if (future.isSuccess()) {
                log.info("连接成功");
                startConsoleThread(((ChannelFuture) future).channel());
            } else if (retry <= 0) {
                log.info("重试次数已用完，连接失败");
            } else {
                //第几次重连
                int order = (MAX_RETRY - retry) + 1;
                //重连间隔，指数递增
                int delay = 1 << order;
                log.info("连接失败，第{}次重连", order);
                bootstrap.config().group().schedule(() -> connect(bootstrap, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }


    public static void startConsoleThread(Channel channel) {
        //TODO: 阻塞在handler中等待用户输入输入  替代新开线程
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
            consoleCommandManager.exec(sc, channel);
        }).start();
    }
}
