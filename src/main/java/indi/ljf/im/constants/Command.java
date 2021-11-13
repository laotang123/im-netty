package indi.ljf.im.constants;

/**
 * @author: ljf
 * @date: 2021/10/23 18:20
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public interface Command {
    byte LOGIN_REQUEST = 1;
    byte LOGIN_RESPONSE = 2;
    byte MESSAGE_REQUEST = 3;
    byte MESSAGE_RESPONSE = 4;
    byte CREATE_GROUP_REQUEST = 5;
    byte CREATE_GROUP_RESPONSE = 6;
    byte GROUP_CHAT_REQUEST = 7;
    byte GROUP_CHAT_RESPONSE = 8;
    byte JOIN_GROUP_REQUEST = 9;
    byte JOIN_GROUP_RESPONSE = 10;
    byte LOGOUT_REQUEST = 11;
    byte LOGOUT_RESPONSE = 12;
}
