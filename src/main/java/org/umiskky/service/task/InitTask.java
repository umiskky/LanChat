package org.umiskky.service.task;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import io.objectbox.BoxStore;
import org.slf4j.Logger;
import org.umiskky.config.ConfigParse;
import org.umiskky.model.dao.FriendDAO;
import org.umiskky.model.dao.GroupMemberDAO;
import org.umiskky.model.dao.LocalUserDAO;
import org.umiskky.model.dao.UserDAO;
import org.umiskky.model.entity.LocalUser;
import org.umiskky.model.entity.MyObjectBox;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.pcaplib.networkcards.PcapNetworkCard;

import java.io.File;
import java.util.HashMap;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public class InitTask {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(InitTask.class);
    public static BoxStore store;
    public static HashMap<String, NetworkCard> networkCardHashMap;
    public static String networkCard;
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
            localUser.setServerPort(0);
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
        store.runInTx(()->{
            UserDAO.removeAll();
            FriendDAO.resetStatus();
            GroupMemberDAO.resetStatus();
        });
    }

    /**
     * @description The method initNetworkCards is used to init network cards.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/19-23:25
     */
    public static void initNetworkCards(){
        networkCardHashMap = PcapNetworkCard.getAllNetworkCards();
    }
}
