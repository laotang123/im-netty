package indi.ljf.im.protocol.command;

import indi.ljf.im.utils.SessionUtil;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author: ljf
 * @date: 2021/10/26 7:56
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class ConsoleCommandManager implements ConsoleCommand {
    private static final Map<String, ConsoleCommand> consoleCommandMap = new HashMap<>();
    private static final String LOGIN_COMMAND = "login";
    private static final LoginConsoleCommand LOGIN_CONSOLE_COMMAND = new LoginConsoleCommand();

    static {
        consoleCommandMap.put(LOGIN_COMMAND, LOGIN_CONSOLE_COMMAND);
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());
        consoleCommandMap.put("quitGroup", new QuitGroupConsoleCommand());
        consoleCommandMap.put("startUserChat", new StartUserChatConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("startGroupChat", new StartGroupChatConsoleCommand());
    }

    @Override
    public void exec(Scanner sc, Channel channel) {
        while (!Thread.interrupted()) {
            String command = sc.nextLine();
            boolean hasLogin = SessionUtil.hasLogin(channel);
            if (hasLogin && LOGIN_COMMAND.equals(command)) {
                System.out.println("该用户已登录，不可重复登录");
            } else if (!hasLogin && consoleCommandMap.containsKey(command) && !LOGIN_COMMAND.equals(command)) {
                System.out.println("该用户未登录，请先登录");
                LOGIN_CONSOLE_COMMAND.exec(sc, channel);
            } else {
                ConsoleCommand consoleCommand = consoleCommandMap.get(command);

                if (consoleCommand != null) {
                    consoleCommand.exec(sc, channel);
                } else {
                    System.out.printf("无法识别指令[%s]，可选指令有 %s，请重新输入\n", command, consoleCommandMap.keySet());
                }
            }
        }
    }
}
