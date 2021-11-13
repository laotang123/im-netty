package indi.ljf.im.common.serialize;

/**
 * @author: ljf
 * @date: 2021/10/23 18:39
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public interface Serializer {
    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();

    byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
