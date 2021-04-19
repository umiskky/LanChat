package org.umiskky.model.entity;

import cn.hutool.core.util.IdUtil;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.umiskky.config.ConfigParse;
import org.umiskky.service.pcap.packet.domain.SymmetricEncryptionKey;

import java.io.File;
import java.io.IOException;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/18
 */
public class LocalUserTest {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LocalUserTest.class);
    private File boxStoreDir;
    private BoxStore store;

    @Before
    public void setUp() throws IOException {
        // store the database in the systems temporary files folder
        File tempFile = File.createTempFile("object-store-test", "");
        // ensure file does not exist so builder creates a directory instead
        tempFile.delete();
        boxStoreDir = tempFile;
        store = MyObjectBox.builder()
                // add directory flag to change where ObjectBox puts its database files
                .directory(boxStoreDir)
                // optional: add debug flags for more detailed ObjectBox log output
                .debugFlags(DebugFlags.LOG_QUERIES | DebugFlags.LOG_QUERY_PARAMETERS)
                .build();
    }

    @After
    public void tearDown() throws Exception {
        if (store != null) {
            store.close();
            store.deleteAllFiles();
        }
    }

    @Test
    public void testPutAndGet() {
        ConfigParse.configParseInit();
        Box<LocalUser> box = store.boxFor(LocalUser.class);

        LocalUser localUser = new LocalUser();
        localUser.setUuid(IdUtil.simpleUUID());
        localUser.setNickname("umiskky");
        localUser.setKey(SymmetricEncryptionKey.getInstance().getKey());
        localUser.setIpAddress("192.168.0.1");
        localUser.setServerPort(8888);

        log.info("put data");
        box.put(localUser);

        LocalUser test = box.query().equal(LocalUser_.uuid, localUser.getUuid()).build().findFirst();
        assert test != null;
        log.info(test.getNickname());

        log.info("Update Test");
        test.setNickname("tee");
        box.put(test);

        LocalUser test2 = box.query().equal(LocalUser_.uuid, localUser.getUuid()).build().findFirst();
        log.info(test2.getNickname());
    }
}
