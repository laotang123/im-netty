package indi.ljf.im.protocol.response;

import indi.ljf.im.constants.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: ljf
 * @date: 2021/10/27 18:42
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JoinGroupResponsePacket extends ResponsePacket {
    private String fromUserName;
    private Integer fromUserId;
    private Integer groupId;

    @Override
    public byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }

}
