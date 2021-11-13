package indi.ljf.im.protocol.command;

import indi.ljf.im.protocol.request.GroupChatRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author: ljf
 * @date: 2021/10/26 11:04
 * @description: 开始聊天，群聊或单聊
 * @modified By:
 * @version: $ 1.0
 */
public class StartGroupChatConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        System.out.println("开始组聊天，输入消息格式如：groupId message");
        while (true) {
            String header = sc.next();
            if (QUIT_COMMAND.equals(header)) {
                System.out.println("退出群聊！");
                break;
            }
            String message = sc.next();
            GroupChatRequestPacket requestPacket = new GroupChatRequestPacket();
            try {
                Integer groupId = Integer.parseInt(header);
                requestPacket.setGroupId(groupId);
                requestPacket.setMessage(message);
                channel.writeAndFlush(requestPacket);
            } catch (Exception e) {
                System.out.printf("消息发送错误，%s\n", e.getMessage());
            }
        }
    }
}
