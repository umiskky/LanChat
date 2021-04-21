package org.umiskky.factories;

import org.slf4j.Logger;
import org.umiskky.service.ExitService;
import org.umiskky.service.InitService;
import org.umiskky.service.task.NetworkCardTask;
import org.umiskky.service.task.pcap.sendtask.*;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public class ServiceDispatcher{

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ServiceDispatcher.class);

    private static final ThreadPoolExecutor serviceThreadPoolExec;
    private static final ThreadPoolExecutor networkThreadPoolExec;
    private static final ThreadPoolExecutor pcapCaptureThreadPoolExec;
    private static final ThreadPoolExecutor pcapSenderThreadPoolExec;
    private static final ScheduledExecutorService pcapScheduledThreadPoolExec;
    private static final ThreadPoolExecutor socketServerThreadPoolExec;
    private static final ThreadPoolExecutor socketClientThreadPoolExec;
    private static final ThreadPoolExecutor packetDispatcherThreadPoolExec;
    private static final ThreadPoolExecutor packetParserThreadPoolExec;
    private static final ThreadPoolExecutor socketParserThreadPoolExec;
    private static final ThreadPoolExecutor guiUpdateThreadPoolExec;
    private static final ThreadPoolExecutor databaseTaskThreadPoolExec;

    private static ViewModelFactory viewModelFactory;

    /*
      @description The method static 初始值设定项 is used to initialize the thread pool.
     * @param null
     * @return
     * @author umiskky
     * @date 2021/4/19-16:19
     */
    static {
        Properties properties = new Properties();
        try {
            properties.load(ServiceDispatcher.class.getResourceAsStream("../config/thread-pool.properties"));
            log.info("Load Thread Pool Config File.");
        } catch (IOException e) {
            e.printStackTrace();
        }


        serviceThreadPoolExec = new ThreadPoolExecutor(
                Integer.parseInt(properties.getProperty("SERVICE.THREAD_POOL.KEEP_ALIVE_TIME.CORE_POOL_SIZE")),
                Integer.parseInt(properties.getProperty("SERVICE.THREAD_POOL.KEEP_ALIVE_TIME.MAX_POOL_SIZE")),
                Long.parseLong(properties.getProperty("SERVICE.THREAD_POOL.KEEP_ALIVE_TIME")),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(Integer.parseInt(properties.getProperty("SERVICE.THREAD_POOL.KEEP_ALIVE_TIME.QUEUE_CAPACITY"))),
                new ThreadPoolExecutor.CallerRunsPolicy());

        networkThreadPoolExec = new ThreadPoolExecutor(
                Integer.parseInt(properties.getProperty("NETWORK_CARD.THREAD_POOL.KEEP_ALIVE_TIME.CORE_POOL_SIZE")),
                Integer.parseInt(properties.getProperty("NETWORK_CARD.THREAD_POOL.KEEP_ALIVE_TIME.MAX_POOL_SIZE")),
                Long.parseLong(properties.getProperty("NETWORK_CARD.THREAD_POOL.KEEP_ALIVE_TIME")),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(Integer.parseInt(properties.getProperty("NETWORK_CARD.THREAD_POOL.KEEP_ALIVE_TIME.QUEUE_CAPACITY"))),
                new ThreadPoolExecutor.CallerRunsPolicy());

        pcapCaptureThreadPoolExec = new ThreadPoolExecutor(
                Integer.parseInt(properties.getProperty("PCAP_CAPTURE.THREAD_POOL.KEEP_ALIVE_TIME.CORE_POOL_SIZE")),
                Integer.parseInt(properties.getProperty("PCAP_CAPTURE.THREAD_POOL.KEEP_ALIVE_TIME.MAX_POOL_SIZE")),
                Long.parseLong(properties.getProperty("PCAP_CAPTURE.THREAD_POOL.KEEP_ALIVE_TIME")),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(Integer.parseInt(properties.getProperty("PCAP_CAPTURE.THREAD_POOL.KEEP_ALIVE_TIME.QUEUE_CAPACITY"))),
                new ThreadPoolExecutor.CallerRunsPolicy());

        pcapSenderThreadPoolExec = new ThreadPoolExecutor(
                Integer.parseInt(properties.getProperty("PCAP_SENDER.THREAD_POOL.KEEP_ALIVE_TIME.CORE_POOL_SIZE")),
                Integer.parseInt(properties.getProperty("PCAP_SENDER.THREAD_POOL.KEEP_ALIVE_TIME.MAX_POOL_SIZE")),
                Long.parseLong(properties.getProperty("PCAP_SENDER.THREAD_POOL.KEEP_ALIVE_TIME")),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(Integer.parseInt(properties.getProperty("PCAP_SENDER.THREAD_POOL.KEEP_ALIVE_TIME.QUEUE_CAPACITY"))),
                new ThreadPoolExecutor.CallerRunsPolicy());

        pcapScheduledThreadPoolExec = new ScheduledThreadPoolExecutor(
                Integer.parseInt(properties.getProperty("PCAP_SCHEDULED.THREAD_POOL.KEEP_ALIVE_TIME.CORE_POOL_SIZE")),
                new ThreadPoolExecutor.CallerRunsPolicy());

        socketServerThreadPoolExec = new ThreadPoolExecutor(
                Integer.parseInt(properties.getProperty("SOCKET_SERVER.THREAD_POOL.KEEP_ALIVE_TIME.CORE_POOL_SIZE")),
                Integer.parseInt(properties.getProperty("SOCKET_SERVER.THREAD_POOL.KEEP_ALIVE_TIME.MAX_POOL_SIZE")),
                Long.parseLong(properties.getProperty("SOCKET_SERVER.THREAD_POOL.KEEP_ALIVE_TIME")),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(Integer.parseInt(properties.getProperty("SOCKET_SERVER.THREAD_POOL.KEEP_ALIVE_TIME.QUEUE_CAPACITY"))),
                new ThreadPoolExecutor.CallerRunsPolicy());

        socketClientThreadPoolExec = new ThreadPoolExecutor(
                Integer.parseInt(properties.getProperty("SOCKET_CLIENT.THREAD_POOL.KEEP_ALIVE_TIME.CORE_POOL_SIZE")),
                Integer.parseInt(properties.getProperty("SOCKET_CLIENT.THREAD_POOL.KEEP_ALIVE_TIME.MAX_POOL_SIZE")),
                Long.parseLong(properties.getProperty("SOCKET_CLIENT.THREAD_POOL.KEEP_ALIVE_TIME")),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(Integer.parseInt(properties.getProperty("SOCKET_CLIENT.THREAD_POOL.KEEP_ALIVE_TIME.QUEUE_CAPACITY"))),
                new ThreadPoolExecutor.CallerRunsPolicy());

        packetDispatcherThreadPoolExec = new ThreadPoolExecutor(
                Integer.parseInt(properties.getProperty("PACKET_DISPATCHER.THREAD_POOL.KEEP_ALIVE_TIME.CORE_POOL_SIZE")),
                Integer.parseInt(properties.getProperty("PACKET_DISPATCHER.THREAD_POOL.KEEP_ALIVE_TIME.MAX_POOL_SIZE")),
                Long.parseLong(properties.getProperty("PACKET_DISPATCHER.THREAD_POOL.KEEP_ALIVE_TIME")),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(Integer.parseInt(properties.getProperty("PACKET_DISPATCHER.THREAD_POOL.KEEP_ALIVE_TIME.QUEUE_CAPACITY"))),
                new ThreadPoolExecutor.CallerRunsPolicy());

        packetParserThreadPoolExec = new ThreadPoolExecutor(
                Integer.parseInt(properties.getProperty("PACKET_PARSE.THREAD_POOL.KEEP_ALIVE_TIME.CORE_POOL_SIZE")),
                Integer.parseInt(properties.getProperty("PACKET_PARSE.THREAD_POOL.KEEP_ALIVE_TIME.MAX_POOL_SIZE")),
                Long.parseLong(properties.getProperty("PACKET_PARSE.THREAD_POOL.KEEP_ALIVE_TIME")),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(Integer.parseInt(properties.getProperty("PACKET_PARSE.THREAD_POOL.KEEP_ALIVE_TIME.QUEUE_CAPACITY"))),
                new ThreadPoolExecutor.CallerRunsPolicy());

        socketParserThreadPoolExec = new ThreadPoolExecutor(
                Integer.parseInt(properties.getProperty("SOCKET_PARSE.THREAD_POOL.KEEP_ALIVE_TIME.CORE_POOL_SIZE")),
                Integer.parseInt(properties.getProperty("SOCKET_PARSE.THREAD_POOL.KEEP_ALIVE_TIME.MAX_POOL_SIZE")),
                Long.parseLong(properties.getProperty("SOCKET_PARSE.THREAD_POOL.KEEP_ALIVE_TIME")),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(Integer.parseInt(properties.getProperty("SOCKET_PARSE.THREAD_POOL.KEEP_ALIVE_TIME.QUEUE_CAPACITY"))),
                new ThreadPoolExecutor.CallerRunsPolicy());

        guiUpdateThreadPoolExec = new ThreadPoolExecutor(
                Integer.parseInt(properties.getProperty("GUI_UPDATE.THREAD_POOL.KEEP_ALIVE_TIME.CORE_POOL_SIZE")),
                Integer.parseInt(properties.getProperty("GUI_UPDATE.THREAD_POOL.KEEP_ALIVE_TIME.MAX_POOL_SIZE")),
                Long.parseLong(properties.getProperty("GUI_UPDATE.THREAD_POOL.KEEP_ALIVE_TIME")),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(Integer.parseInt(properties.getProperty("GUI_UPDATE.THREAD_POOL.KEEP_ALIVE_TIME.QUEUE_CAPACITY"))),
                new ThreadPoolExecutor.CallerRunsPolicy());

        databaseTaskThreadPoolExec = new ThreadPoolExecutor(
                Integer.parseInt(properties.getProperty("DATABASE_TASK.THREAD_POOL.KEEP_ALIVE_TIME.CORE_POOL_SIZE")),
                Integer.parseInt(properties.getProperty("DATABASE_TASK.THREAD_POOL.KEEP_ALIVE_TIME.MAX_POOL_SIZE")),
                Long.parseLong(properties.getProperty("DATABASE_TASK.THREAD_POOL.KEEP_ALIVE_TIME")),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(Integer.parseInt(properties.getProperty("DATABASE_TASK.THREAD_POOL.KEEP_ALIVE_TIME.QUEUE_CAPACITY"))),
                new ThreadPoolExecutor.CallerRunsPolicy());
        log.info("Initial Thread Pool Executors.");
    }

    public static void setViewModelFactory(ViewModelFactory viewModelFactory) {
        ServiceDispatcher.viewModelFactory = viewModelFactory;
    }

    public static void submitTask(InitService initService){
        initService.initService();
    }

    public static void submitTask(ExitService exitService){
        exitService.exitService();
    }

    public static void submitTask(SendHelloPacketTask sendHelloPacketTask){
        pcapSenderThreadPoolExec.execute(sendHelloPacketTask);
    }

    public static void submitTask(SendInviteToGroupPacketTask sendInviteToGroupPacketTask){
        pcapSenderThreadPoolExec.execute(sendInviteToGroupPacketTask);
    }

    public static void submitTask(SendMakeFriendsPacketTask sendMakeFriendsPacketTask){
        pcapSenderThreadPoolExec.execute(sendMakeFriendsPacketTask);
    }

    public static void submitTask(SendNotifyPacketTask sendNotifyPacketTask){
        pcapSenderThreadPoolExec.execute(sendNotifyPacketTask);
    }

    public static void submitTask(SendStatusAckPacketTask sendStatusAckPacketTask){
        pcapSenderThreadPoolExec.execute(sendStatusAckPacketTask);
    }

    public static void submitTask(NetworkCardTask networkCardTask){
        networkThreadPoolExec.execute(networkCardTask);
    }
}
