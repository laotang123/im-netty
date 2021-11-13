package indi.ljf.im.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author: ljf
 * @date: 2021/10/23 18:17
 * @description: 协议传输包定义
 * 【[魔数](4 字节)--[版本号](1字节)--[序列化算法](1字节)--[指令](1字节)--[数据长度](4字节)--[数据]】
 * @modified By:
 * @version: $ 1.0
 */
@Data
public abstract class Packet {
    @JSONField(serialize = false, deserialize = false)
    private final byte version = 1;

    @JSONField(serialize = false)
    public abstract byte getCommand();
}
