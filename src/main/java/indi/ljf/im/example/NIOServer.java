package indi.ljf.im.example;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: ljf
 * @date: 2021/10/22 9:51
 * @description: serverSelector负责接入新建连接
 * clientSelector负责读写数据
 * @modified By:
 * @version: $ 1.0
 */
@Slf4j
public class NIOServer {
    public static void main(String[] args) throws IOException {
        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();
        int port = 8081;

        //轮询 查看连接事件
        new Thread(() -> {
            try {
                //开启监听端口
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(port));
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
                log.info("开始监听{}端口", port);
                while (true) {
                    if (serverSelector.select(100) > 0) {
                        Set<SelectionKey> selectionKeys = serverSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                        while (keyIterator.hasNext()) {
                            SelectionKey selectionKey = keyIterator.next();
                            //可连接事件
                            if (selectionKey.isAcceptable()) {
                                try {
                                    //来一个新连接注册到selector上，而不是新开一个线程
                                    SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                                    socketChannel.configureBlocking(false);
                                    //监听读事件
                                    socketChannel.register(clientSelector, SelectionKey.OP_READ);
                                    log.info("接收连接：{}", socketChannel);
                                } finally {
                                    keyIterator.remove();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
