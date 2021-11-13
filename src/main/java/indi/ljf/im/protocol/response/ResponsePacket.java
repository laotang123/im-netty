package indi.ljf.im.protocol.response;

import indi.ljf.im.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: ljf
 * @date: 2021/10/27 19:23
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ResponsePacket extends Packet {
    private boolean success;
    private String message;
}
