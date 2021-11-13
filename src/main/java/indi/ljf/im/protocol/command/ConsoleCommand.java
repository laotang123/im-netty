package indi.ljf.im.protocol.command;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author: ljf
 * @date: 2021/10/26 7:55
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public interface ConsoleCommand {
    String QUIT_COMMAND = "quit";
    String LOGIN_COMMAND = "login";

    void exec(Scanner sc, Channel channel);
}
