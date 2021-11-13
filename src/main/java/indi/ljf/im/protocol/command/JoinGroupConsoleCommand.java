package indi.ljf.im.protocol.command;

import indi.ljf.im.protocol.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author: ljf
 * @date: 2021/10/26 8:02
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        System.out.print("加入群聊，输入群聊id: ");
        while (true) {
            String line = sc.nextLine();
            try {
                Integer groupId = Integer.parseInt(line);
                JoinGroupRequestPacket requestPacket = new JoinGroupRequestPacket();
                requestPacket.setGroupId(groupId);
                channel.writeAndFlush(requestPacket);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("群聊id格式错误，请重新输入：");
            }

        }
    }
}
