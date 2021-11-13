package indi.ljf.im.protocol.command;

import indi.ljf.im.common.codec.PacketCodeC;
import indi.ljf.im.protocol.request.MessageRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author: ljf
 * @date: 2021/10/26 8:02
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class StartUserChatConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        while (true) {
            String header = sc.next();
            if (QUIT_COMMAND.equals(header)) {
                System.out.println("退出单聊，请输入新指令");
                break;
            }
            String message = sc.next();

            try {
                MessageRequestPacket requestPacket = new MessageRequestPacket();
                requestPacket.setMessage(message);
                Integer toUserId = Integer.parseInt(header);
                requestPacket.setToUserId(toUserId);
                ByteBuf byteBuf = PacketCodeC.getInstance().encode(channel.alloc(), requestPacket);
                channel.writeAndFlush(byteBuf);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
