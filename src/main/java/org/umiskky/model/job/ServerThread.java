package org.umiskky.model.job;

import org.umiskky.model.job.server.RequestProcessor;
import org.umiskky.model.utils.SystemFreePort;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerThread implements Runnable {
    SystemFreePort systemFreePort = null;
    private static int port = -1;
    private static ServerSocket serverSocket;
    private static ThreadPoolExecutor threadPoolExecutor;

    public ServerThread(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public static int getPort() {
        return port;
    }


    @Override
    public void run() {
        try {
            while (port == -1) {
                systemFreePort = new SystemFreePort();
                port = systemFreePort.getPort();
            }
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
