package indi.ljf.im.common.codec;

import indi.ljf.im.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author: ljf
 * @date: 2021/10/24 15:30
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        PacketCodeC.getInstance().encode(out, packet);
    }
}
