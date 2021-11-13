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
public class ChannelInBoundHandlerC extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel inBound C read: " + ((ByteBuf) msg).toString(StandardCharsets.UTF_8));

        ByteBuf byteBuf = (ByteBuf) msg;
//        byteBuf.markReaderIndex();

//        int len = byteBuf.readableBytes();
//        byte[] bytes = new byte[len];
//        byteBuf.readBytes(bytes);
//        ByteBuf buffer = ctx.alloc().buffer();
//        buffer.writeBytes(bytes);
//        byteBuf.resetReaderIndex();
//        ctx.writeAndFlush(byteBuf);
        ctx.channel().writeAndFlush(byteBuf);

//        ReferenceCountUtil.release(msg);
    }
}
