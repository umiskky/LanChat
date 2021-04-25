package org.umiskky.service.task.socket;

import org.slf4j.Logger;
import org.umiskky.model.entity.Message;
import org.umiskky.service.task.socket.utils.IOUtil;
import org.umiskky.service.task.socket.utils.SocketUtil;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread implements Runnable{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ClientThread.class);
    private final String ip;
    private final int port;
    private Message message;

    public ClientThread(String ip, int port, Message message) {
        this.ip = ip;
        this.message = message;
        this.port = port;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(Thread.currentThread().getName() + "(Socket_Client_Thread)");
        Socket clientSocket = null;
        ObjectOutputStream oos = null;
        try {
            clientSocket = new Socket(ip,port);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            IOUtil.close(oos);
            SocketUtil.close(clientSocket);
        }
    }

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
