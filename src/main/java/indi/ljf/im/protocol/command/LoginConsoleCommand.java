package indi.ljf.im.protocol.command;

import indi.ljf.im.common.codec.PacketCodeC;
import indi.ljf.im.protocol.request.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author: ljf
 * @date: 2021/10/26 8:00
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        System.out.print("输入姓名：");
        String name = sc.nextLine();
        System.out.print("输入密码：");
        String password = sc.nextLine();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserName(name);
        loginRequestPacket.setPassword(password);
        ByteBuf byteBuf = PacketCodeC.getInstance().encode(channel.alloc(), loginRequestPacket);
        channel.writeAndFlush(byteBuf);
    }
}
