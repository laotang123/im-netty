package indi.ljf.im.example.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author: ljf
 * @date: 2021/10/24 17:14
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@Slf4j
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; i++) {
            log.info("第{}次发送数据", i);
            ByteBuf byteBuf = getByteBuffer(ctx);
            ctx.channel().writeAndFlush(byteBuf);
//            Thread.sleep(10);
        }
    }

    public ByteBuf getByteBuffer(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer();

        byte[] bytes = "你好，欢迎关注刘俊峰的github。\n".getBytes(StandardCharsets.UTF_8);
        buffer.writeBytes(bytes);
        return buffer;
    }
}
