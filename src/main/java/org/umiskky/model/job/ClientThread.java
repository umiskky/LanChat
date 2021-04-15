package org.umiskky.model.job;


import org.umiskky.model.domain.Message;
import org.umiskky.model.utils.IOUtil;
import org.umiskky.model.utils.SocketUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Setter
@Getter
public class ClientThread  implements Runnable{
private static String ip;
private static int port;
private static Message message;

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
