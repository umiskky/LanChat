package org.umiskky.service.task.socket;

import org.slf4j.Logger;
import org.umiskky.service.task.socket.server.RequestProcessor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerThread implements Runnable {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ServerThread.class);
    private int port = -1;
    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPoolExecutor;

    public ServerThread() {
    }

    public ServerThread(ThreadPoolExecutor threadPoolExecutor, int port) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.port = port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(Thread.currentThread().getName() + "(Socket_Server_Thread)");
        try {
            serverSocket = new ServerSocket(port);
            log.info("Start socket server.");
            while (true) {
                if(Thread.currentThread().isInterrupted()){
                    serverSocket.close();
                    break;
                }
                Socket socket = serverSocket.accept();
                log.info("客户:" + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                threadPoolExecutor.submit(new RequestProcessor(socket));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
