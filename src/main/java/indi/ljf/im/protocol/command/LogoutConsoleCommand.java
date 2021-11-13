package indi.ljf.im.protocol.command;

import indi.ljf.im.protocol.request.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author: ljf
 * @date: 2021/10/26 8:01
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        LogoutRequestPacket requestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(requestPacket);
    }
}
