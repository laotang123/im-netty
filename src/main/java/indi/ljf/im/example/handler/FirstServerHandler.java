package indi.ljf.im.example.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author: ljf
 * @date: 2021/10/24 17:16
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@Slf4j
public class FirstServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("收到客户端请求: {}", msg.toString(StandardCharsets.UTF_8));
    }
}
