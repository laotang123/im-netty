package indi.ljf.im.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author: ljf
 * @date: 2021/10/24 14:49
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(PacketCodeC.getInstance().decode(in));
    }
}
