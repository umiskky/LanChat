package org.umiskky.service.task;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import io.objectbox.BoxStore;
import org.pcap4j.packet.namednumber.EtherType;
import org.slf4j.Logger;
import org.umiskky.config.ConfigParse;
import org.umiskky.factories.ServiceDispatcher;
import org.umiskky.model.dao.FriendDAO;
import org.umiskky.model.dao.GroupMemberDAO;
import org.umiskky.model.dao.LocalUserDAO;
import org.umiskky.model.dao.UserDAO;
import org.umiskky.model.entity.LocalUser;
import org.umiskky.model.entity.MyObjectBox;
import org.umiskky.service.ExitService;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.pcaplib.networkcards.PcapNetworkCard;
import org.umiskky.service.task.socket.ServerThread;
import org.umiskky.service.task.socket.utils.SystemFreePort;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public class InitTask {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(InitTask.class);
    public static BoxStore store;
    public static HashMap<String, NetworkCard> networkCardsMapByName;
    public static HashMap<String, NetworkCard> networkCardsMapByLinkLayerAddr;
    public static NetworkCard networkCardSelected;
    public static LocalUser localUser;


    /**
     * @description The method importConfig is used to construct a task to import config.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/19-18:23
     */
    public static void importConfig(){
        ConfigParse.configParseInit();
        log.info("Import config.");
    }

    /**
     * @description The method initDatabase is used to init database.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/19-23:22
     */
    public static void initDatabase(){
        store = MyObjectBox.builder().baseDirectory(new File(InitTask.class.getResource("/").getPath()+"org/umiskky/model/database/")).name("lan-chat-db").build();
        log.info("Init the database.");
        store.runInTx(()->{
            LocalUser tmpLocalUser = LocalUserDAO.getLocalUser();
            if(tmpLocalUser == null) {
                tmpLocalUser = new LocalUser();
                tmpLocalUser.setUuid(IdUtil.simpleUUID());
                tmpLocalUser.setKey(SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded());
                LocalUserDAO.putLocalUser(tmpLocalUser);
            }
            localUser = LocalUserDAO.getLocalUser();
            try {
                localUser.setServerPort(new SystemFreePort().getAndReleasePort());
            } catch (IOException e) {
                e.printStackTrace();
            }
            localUser.setIpAddress("");
            LocalUserDAO.putLocalUser(localUser);
        });
        log.info("Init the LocalUser.");
    }

    /**
     * @description The method cleanDatabase is used to clean database relative entity's status.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/19-23:22
     */
    public static void cleanDatabase(){
        UserDAO.removeAll();
        FriendDAO.resetStatus();
        GroupMemberDAO.resetStatus();
    }

    /**
     * @description The method initNetworkCards is used to init network cards.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/19-23:25
     */
    public static void initNetworkCards(){
        networkCardsMapByName = (HashMap<String, NetworkCard>) PcapNetworkCard.getAllNetworkCards().get("networkCardsMapByName");
        networkCardsMapByLinkLayerAddr = (HashMap<String, NetworkCard>) PcapNetworkCard.getAllNetworkCards().get("networkCardsMapByLinkLayerAddr");
    }

    /**
     * @description The method initEthernetTypeCode is used to init Ethernet type code.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/20-16:45
     */
    public static void initEthernetTypeCode() {
        EtherType helloEtherType = new EtherType((short) 0xAAA0, "HELLO");
        EtherType.register(helloEtherType);

        EtherType makeFriendsEtherType = new EtherType((short) 0xAAA1, "MAKE_FRIENDS");
        EtherType.register(makeFriendsEtherType);

        EtherType statusAckEtherType = new EtherType((short) 0xAAA2, "STATUS_ACK");
        EtherType.register(statusAckEtherType);

        EtherType inviteToGroupEtherType = new EtherType((short) 0xAAA3, "INVITE_TO_GROUP");
        EtherType.register(inviteToGroupEtherType);

        EtherType notifyEtherType = new EtherType((short) 0xAAA4, "NOTIFY");
        EtherType.register(notifyEtherType);
    }

    /**
     * @description The method launchNetworkCardTasks is used to make network cards ready to work.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/20-23:50
     */
    public static void launchNetworkCardTasks(){
        for(NetworkCard networkCard : networkCardsMapByName.values()){
            ServiceDispatcher.submitTask(new NetworkCardTask(networkCard));
        }
    }

    /**
     * @description The method launchSocketServerTask is used to init socket server.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/22-13:49
     */
    public static void launchSocketServerTask(){
        ServerThread serverThread = new ServerThread();
        ServiceDispatcher.submitTask(serverThread);
    }

    public static void addShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new ExitService());
    }
}
