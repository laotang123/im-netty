package indi.ljf.im.common.codec;

import indi.ljf.im.constants.Command;
import indi.ljf.im.common.serialize.Serializer;
import indi.ljf.im.protocol.Packet;
import indi.ljf.im.protocol.request.*;
import indi.ljf.im.protocol.response.*;
import indi.ljf.im.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ljf
 * @date: 2021/10/23 19:09
 * @description: 懒汉式 单例模式三要素
 * 1、私有构造函数
 * 2、volatile 防止指令重排序
 * 3、双重检查  锁的细化
 * @modified By:
 * @version: $ 1.0
 */
@Slf4j
public class PacketCodeC {
    public static final int MAGIC_NUMBER = 0x12345678;
    private static volatile PacketCodeC INSTANCE;
    private static final Map<Byte, Class<? extends Packet>> packetMap = new HashMap<>();

    static {
        packetMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetMap.put(Command.GROUP_CHAT_REQUEST, GroupChatRequestPacket.class);
        packetMap.put(Command.GROUP_CHAT_RESPONSE, GroupChatResponsePacket.class);
        packetMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetMap.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetMap.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetMap.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);
    }

    private PacketCodeC() {
    }

    public static PacketCodeC getInstance() {
        if (INSTANCE == null) {
            synchronized (PacketCodeC.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PacketCodeC();
                }
            }
        }
        return INSTANCE;
    }

    public ByteBuf encode(ByteBufAllocator allocator, Packet packet) {
        ByteBuf byteBuf = allocator.ioBuffer();
        encode(byteBuf, packet);
        return byteBuf;
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }



    public Packet decode(ByteBuf byteBuf) {
        PacketUtil.skipMagicNumber(byteBuf);
        PacketUtil.skipVersion(byteBuf);

        byte serializeAlgo = byteBuf.readByte();

        byte command = byteBuf.readByte();

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgo);

        if (requestType != null && serializer != null) {
            byte[] bytes = PacketUtil.readContent(byteBuf);
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgo) {
        if (serializeAlgo == Serializer.JSON_SERIALIZER) {
            return Serializer.DEFAULT;
        }
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        Class<? extends Packet> packetClass = packetMap.get(command);
        if (packetClass == null) {
            log.info("请求指令不存在：{}", command);
            return null;
        }
        return packetClass;
    }
}

