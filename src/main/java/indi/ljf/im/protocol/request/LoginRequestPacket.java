package indi.ljf.im.protocol.request;

import indi.ljf.im.constants.Command;
import indi.ljf.im.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: ljf
 * @date: 2021/10/23 18:35
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket extends Packet {
    private String userName;
    private String password;

    @Override
    public byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

}
