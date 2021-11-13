package indi.ljf.im.protocol.response;

import indi.ljf.im.constants.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: ljf
 * @date: 2021/10/27 21:51
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogoutResponsePacket extends ResponsePacket {
    @Override
    public byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
