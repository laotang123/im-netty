package indi.ljf.im.protocol.request;

import indi.ljf.im.constants.Command;
import indi.ljf.im.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: ljf
 * @date: 2021/10/23 22:48
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageRequestPacket extends Packet {
    private Integer toUserId;
    private String message;

    @Override
    public byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
