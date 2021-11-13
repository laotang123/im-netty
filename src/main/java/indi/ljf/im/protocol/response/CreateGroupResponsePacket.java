package indi.ljf.im.protocol.response;

import indi.ljf.im.constants.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: ljf
 * @date: 2021/10/26 11:09
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateGroupResponsePacket extends ResponsePacket {
    private Integer groupId;
    private List<String> userNameList;

    @Override
    public byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
