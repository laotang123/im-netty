package indi.ljf.im.protocol.request;

import indi.ljf.im.constants.Command;
import indi.ljf.im.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: ljf
 * @date: 2021/10/26 10:23
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateGroupRequestPacket extends Packet {
    private List<Integer> userIdList;

    @Override
    public byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
