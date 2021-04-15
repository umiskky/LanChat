package org.umiskky.model.utils;

import lombok.Getter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;

/**
 * @apiNote 获取系统空闲端口号
 */
@Getter
public class SystemFreePort {
    private static Random random = new Random();
    private Socket socket;
    private static final int START = 1024;
    private static final int END = 65530;
    /**
     * @apiNote 获取系统空闲端口，并占用该端口资源
     */
    public SystemFreePort() throws IOException {
        socket = new Socket();
        /**
         * InetSocketAddress构造函数InetSocketAddress(int port)当入参port为0时，由系统自动分配一个临时端口
         */
        InetSocketAddress inetSocketAddress = new InetSocketAddress(0);
        socket.bind(inetSocketAddress);
    }


    /**
     * @apiNote 释放该端口
     */
    public void releasePort() throws IOException{
        if (null == this.socket || this.socket.isClosed())
        {
            return;
        }
        socket.close();
    }
    /**
     * 返回端口,不释放端口
     */
    public int getPort(){
        if (null == this.socket || this.socket.isClosed())
        {
            return -1;
        }
        return socket.getLocalPort();
    }

    /**
     * 返回端口并释放资源
     */
    public int getAndReleasePort() throws IOException{
        if (null == this.socket || this.socket.isClosed())
        {
            return -1;
        }
        this.socket.close();
        return socket.getLocalPort();
    }

    /**
     * 获取随机数
     */
    public static int getRandomNumber(){
        int res = random.nextInt(Math.abs(END - START)) + START;
        return  res;
    }
}
