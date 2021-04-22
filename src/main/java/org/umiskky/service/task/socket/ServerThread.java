package org.umiskky.service.task.socket;

import org.umiskky.service.task.socket.server.RequestProcessor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerThread implements Runnable {
    private int port = -1;
    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPoolExecutor;

    public ServerThread() {
    }

    public ServerThread(ThreadPoolExecutor threadPoolExecutor, int port) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("客户:" + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                threadPoolExecutor.submit(new RequestProcessor(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
