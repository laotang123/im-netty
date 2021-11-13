package indi.ljf.im.example.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author: ljf
 * @date: 2021/10/24 9:16
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class ChannelInBoundHandlerB extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel inBound B read: "+((ByteBuf)msg).toString(StandardCharsets.UTF_8));
        super.channelRead(ctx, msg);
    }
}
