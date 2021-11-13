package indi.ljf.im.protocol.response;

import indi.ljf.im.constants.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: ljf
 * @date: 2021/10/26 14:10
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupChatResponsePacket extends ResponsePacket {
    private String fromUserName;
    private Integer groupId;

    @Override
    public byte getCommand() {
        return Command.GROUP_CHAT_RESPONSE;
    }
}
