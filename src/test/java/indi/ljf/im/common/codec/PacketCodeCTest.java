package indi.ljf.im.common.codec;

import indi.ljf.im.protocol.Packet;
import indi.ljf.im.protocol.request.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author: ljf
 * @date: 2021/10/23 20:37
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class PacketCodeCTest {
    @Test
    public void test01() {
        PacketCodeC packetCodeC = PacketCodeC.getInstance();
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setPassword("0000");
        packet.setUserName("ljf");

        ByteBuf byteBuf = packetCodeC.encode(ByteBufAllocator.DEFAULT, packet);
        System.out.println(byteBuf.readableBytes());
        System.out.println(byteBuf.toString(StandardCharsets.UTF_8));

        Packet decode = packetCodeC.decode(byteBuf);
        System.out.println(decode.getVersion());
        System.out.println(decode.getCommand());
        System.out.println(decode);
    }
}
