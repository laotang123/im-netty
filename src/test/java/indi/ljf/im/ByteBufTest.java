package indi.ljf.im;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author: ljf
 * @date: 2021/10/23 11:26
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        //write增加写指针，writeIndex不到capacity，仍然可写
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1,2,3,4)", buffer);

        //ini类型占4个字节
        buffer.writeInt(12);
        print("write int(12)", buffer);

        //写指针等于capacity，表示不可写
        buffer.writeByte(1);
        print("write byte(1)", buffer);

        //不可写之后，再写会导致扩容
        buffer.writeByte(6);
        print("write byte(6)", buffer);

        //get方法不改变读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));

        print("getBytes ", buffer);
    }

    public static void print(String action, ByteBuf byteBuf) {
        System.out.println("after==========" + action + "=============");
        System.out.println("capacity(): " + byteBuf.capacity());
        System.out.println("maxCapacity(): " + byteBuf.maxCapacity());
        System.out.println("readerIndex(): " + byteBuf.readerIndex());
        System.out.println("readableBytes(): " + byteBuf.readableBytes());
        System.out.println("isReadable(): " + byteBuf.isReadable());
        System.out.println("writerIndex(): " + byteBuf.writerIndex());
        System.out.println("writableBytes(): " + byteBuf.writableBytes());
        System.out.println("isWritable(): " + byteBuf.isWritable());
        System.out.println("maxWritableBytes(): " + byteBuf.maxWritableBytes());
        System.out.println();
    }
}
