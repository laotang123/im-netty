package indi.ljf.im.protocol.command;

import indi.ljf.im.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author: ljf
 * @date: 2021/10/26 8:02
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        System.out.println("【拉人群聊】输入userId列表，userId之间英文逗号隔开：");
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        String[] userIds = sc.nextLine().split(",");
        List<Integer> userIdList = new ArrayList<>(userIds.length);
        int userId;
        for (String strId : userIds) {
            try {
                userId = Integer.parseInt(strId);
                userIdList.add(userId);
            } catch (Exception e) {
                System.out.println(strId + " " + e.getMessage());
            }
        }
        createGroupRequestPacket.setUserIdList(userIdList);
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
