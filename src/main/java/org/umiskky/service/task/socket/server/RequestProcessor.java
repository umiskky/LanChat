package org.umiskky.service.task.socket.server;

import org.slf4j.Logger;
import org.umiskky.model.dao.FriendDAO;
import org.umiskky.model.dao.MessageDAO;
import org.umiskky.model.entity.Friend;
import org.umiskky.model.entity.Message;
import org.umiskky.service.task.socket.utils.IOUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
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
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(currentClientSocket.getInputStream());
            Message message = (Message) ois.readObject();
            if(message != null){
                String uuid = message.getFromUuid();
                if(uuid != null && !uuid.isEmpty()){
                    Friend friend = FriendDAO.getFriendById(uuid);
                    if(friend != null){
                        Message.decryptMessage(message, friend.getKey());
                        message.setId(0);
                        MessageDAO.putMessage(message);
                        log.debug("Get a message.\n" + message);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage());
        } finally {
            IOUtil.close(ois);
        }
    }
}
