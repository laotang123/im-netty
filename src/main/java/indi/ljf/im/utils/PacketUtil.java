package indi.ljf.im.utils;

import io.netty.buffer.ByteBuf;

/**
 * @author: ljf
 * @date: 2021/10/23 19:20
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class PacketUtil {
    public static void skipMagicNumber(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);
    }

    public static void skipVersion(ByteBuf byteBuf) {
        byteBuf.skipBytes(1);
    }

    public static byte[] readContent(ByteBuf byteBuf) {
        int len = byteBuf.readInt();
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);
        return bytes;
    }
}
