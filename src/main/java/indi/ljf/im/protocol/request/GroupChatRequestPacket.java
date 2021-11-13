package indi.ljf.im.protocol.request;

import indi.ljf.im.constants.Command;
import indi.ljf.im.protocol.Packet;
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
public class GroupChatRequestPacket extends Packet {
    private Integer groupId;
    private String message;

    @Override
    public byte getCommand() {
        return Command.GROUP_CHAT_REQUEST;
    }
}
