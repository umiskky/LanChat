package org.umiskky.service.task.socket.server;

import org.slf4j.Logger;
import org.umiskky.model.dao.FriendDAO;
import org.umiskky.model.dao.MessageDAO;
import org.umiskky.model.entity.Friend;
import org.umiskky.model.entity.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @apiNote 处理服务器接收到的对象
 */
public class RequestProcessor implements Runnable {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RequestProcessor.class);
    private Socket currentClientSocket;

    public RequestProcessor(Socket currentClientSocket) {
        this.currentClientSocket = currentClientSocket;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(Thread.currentThread().getName() + "(Message_Parse_Thread)");
        try {
            OnlineClientIOCache currentClientIOCache = new OnlineClientIOCache(
                    new ObjectInputStream(currentClientSocket.getInputStream()),
                    new ObjectOutputStream(currentClientSocket.getOutputStream()));
            while (true){
                //不停地读取客户端发过来的请求对象
                Message message = (Message) currentClientIOCache.getOis().readObject();
                //待处理Message
                if(message != null){
                    String uuid = message.getFromUuid();
                    if(uuid != null && !uuid.isEmpty()){
                        Friend friend = FriendDAO.getFriendById(uuid);
                        if(friend != null){
                            Message newMessage = Message.decryptMessage(message, friend.getKey());
                            newMessage.setId(0);
                            MessageDAO.putMessage(newMessage);
                            log.debug("Get a message.\n" + newMessage);
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
