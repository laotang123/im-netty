package indi.ljf.im.example.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author: ljf
 * @date: 2021/10/24 9:16
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class ChannelOutBoundHandlerA extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("channel outBound A write: " + ((ByteBuf) msg).toString(StandardCharsets.UTF_8));
        ReferenceCountUtil.release(msg);
//        super.write(ctx, msg, promise);
    }
}
