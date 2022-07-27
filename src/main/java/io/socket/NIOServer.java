package io.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.configureBlocking(false);
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        ServerSocket serverSocket = ssChannel.socket();
        InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 8888);
        serverSocket.bind(addr);

        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {
                    ServerSocketChannel ssChannel1 = (ServerSocketChannel) key.channel();

                    //为连接创建新的socket
                    SocketChannel sChannel = ssChannel1.accept();
                    System.out.printf("客户端发起连接 ip: %s \n", sChannel.getRemoteAddress());
                    sChannel.configureBlocking(false);
                    //用于从客户端读取数据
                    sChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    System.out.println(readDataFromSocketChannel(socketChannel));
                    socketChannel.close();
                }

                keyIterator.remove();
            }
        }
    }

    private static String readDataFromSocketChannel(SocketChannel socketChannel) throws IOException {
        ByteBuffer buf = ByteBuffer.allocateDirect(4 * 1024);
        StringBuilder data = new StringBuilder();

        while (true) {
            int n = socketChannel.read(buf);

            if (n == -1) {
                break;
            }

            buf.flip();

            int limit = buf.limit();
            char[] tmp = new char[limit];

            for (int i = 0; i < limit ;i++) {
                tmp[i] = (char) buf.get(i);
            }

            data.append(tmp);
            buf.clear();
        }

        return data.toString();
    }
}
