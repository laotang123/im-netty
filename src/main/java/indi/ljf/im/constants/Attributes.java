package indi.ljf.im.constants;

import indi.ljf.im.protocol.Session;
import io.netty.util.AttributeKey;

/**
 * @author: ljf
 * @date: 2021/10/23 23:22
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
