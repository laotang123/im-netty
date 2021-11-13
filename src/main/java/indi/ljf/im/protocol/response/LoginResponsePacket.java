package indi.ljf.im.protocol.response;

import indi.ljf.im.constants.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: ljf
 * @date: 2021/10/23 21:29
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponsePacket extends ResponsePacket {
    private Integer userId;
    private String userName;

    @Override
    public byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
