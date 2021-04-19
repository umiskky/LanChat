package org.umiskky.service.task.socket;


import lombok.Getter;
import lombok.Setter;
import org.umiskky.model.entity.Message;
import org.umiskky.service.task.socket.utils.IOUtil;
import org.umiskky.service.task.socket.utils.SocketUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Setter
@Getter
public class ClientThread  implements Runnable{
    private String ip;
    private int port;
    private Message message;

    public ClientThread(String ip, int port, Message message) {
        this.ip = ip;
        this.message = message;
        this.port = port;
    }

    @Override
    public void run() {
        Socket clientSocket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            clientSocket = new Socket(ip,port);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(oos);
            IOUtil.close(ois);
            SocketUtil.close(clientSocket);
        }
    }
}
