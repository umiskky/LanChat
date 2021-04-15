package org.umiskky.model.job.server;

import org.umiskky.model.domain.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @apiNote 处理服务器接收到的对象
 */
public class RequestProcessor implements Runnable {
    private Socket currentClientSocket;

    public RequestProcessor(Socket currentClientSocket) {
        this.currentClientSocket = currentClientSocket;
    }

    @Override
    public void run() {
        boolean flag = true;
        try {
            OnlineClientIOCache currentClientIOCache = new OnlineClientIOCache(
                    new ObjectInputStream(currentClientSocket.getInputStream()),
                    new ObjectOutputStream(currentClientSocket.getOutputStream()));
            while (flag){
                //不停地读取客户端发过来的请求对象
                Message message = (Message) currentClientIOCache.getOis().readObject();
                //待处理Message
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
