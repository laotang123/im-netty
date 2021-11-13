package indi.ljf.im.common.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @author: ljf
 * @date: 2021/10/23 18:42
 * @description: json序列化对象
 * 阿里巴巴fastjson
 * @modified By:
 * @version: $ 1.0
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
