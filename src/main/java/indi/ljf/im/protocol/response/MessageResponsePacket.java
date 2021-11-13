package indi.ljf.im.protocol.response;

import indi.ljf.im.constants.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: ljf
 * @date: 2021/10/23 23:04
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageResponsePacket extends ResponsePacket {
    private Integer fromUserId;
    private String fromUserName;

    @Override
    public byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
