package indi.ljf.im.protocol.request;

import indi.ljf.im.constants.Command;
import indi.ljf.im.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: ljf
 * @date: 2021/10/27 18:38
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JoinGroupRequestPacket extends Packet {
    private Integer groupId;

    @Override
    public byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }
}
