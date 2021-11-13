package indi.ljf.im.server;

import indi.ljf.im.common.codec.CheckProtocolFrameDecoder;
import indi.ljf.im.common.codec.PacketDecoder;
import indi.ljf.im.common.codec.PacketEncoder;
import indi.ljf.im.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: ljf
 * @date: 2021/10/23 9:24
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@Slf4j
public class NettyServer {
    static final int PORT = 8080;

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boosGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("boos"));
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("worker"));

        //共享handler
        MetricHandler metricHandler = new MetricHandler();

        serverBootstrap.group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //日志打印
                        ch.pipeline().addLast("LoggingHandler", new LoggingHandler(LogLevel.DEBUG));
                        //指标统计
                        ch.pipeline().addLast("MetricHandler", metricHandler);
                        //检查魔数和粘包半包
                        ch.pipeline().addLast("CheckProtocolFrameDecoder", new CheckProtocolFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        //消息解码
                        ch.pipeline().addLast("PacketDecoder", new PacketDecoder());

                        //处理客户端登录
                        ch.pipeline().addLast("LoginRequestHandler", new LoginRequestHandler());
                        //指令消息处理
                        ch.pipeline().addLast("CommandRequestHandler", new CommandRequestHandler());

                        //热插拔身份校验
                        ch.pipeline().addLast("AuthHandler", new AuthHandler());
                        //单用户消息处理
                        ch.pipeline().addLast("MessageRequestHandler", new SingleChatRequestHandler());
                        //创建群聊处理
//                        ch.pipeline().addLast("CreateGroupRequestHandler", new CreateGroupRequestHandler());
                        //加入群聊
//                        ch.pipeline().addLast("JoinGroupRequestHandler", new JoinGroupRequestHandler());
                        //群聊消息处理
                        ch.pipeline().addLast("GroupChatRequestHandler", new GroupChatRequestHandler());
                        //退出群聊响应处理
//                        ch.pipeline().addLast("QuitGroupRequestHandler", new QuitGroupRequestHandler());
                        //退出账户响应
//                        ch.pipeline().addLast("LogoutRequestHandler", new LogoutRequestHandler());
                        //消息编码为二进制流
                        ch.pipeline().addLast("PacketEncoder", new PacketEncoder());
                    }
                });
        bind(serverBootstrap, PORT);
    }

    /**
     * 递增绑定端口
     */
    public static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("绑定{}成功", port);
            } else {
                log.info("绑定{}失败，尝试{}", port, port + 1);
                bind(serverBootstrap, port + 1);
            }
        });
    }


}
