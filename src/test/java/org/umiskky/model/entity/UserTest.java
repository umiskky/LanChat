package org.umiskky.model.entity;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.umiskky.model.dao.UserDAO;
import org.umiskky.service.InitService;

import java.io.File;
import java.io.IOException;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
public class UserTest {
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
    public void testUser() {
        Box<User> userBox1 = store.boxFor(User.class);
        Box<User> userBox2 = store.boxFor(User.class);

//        Thread thread1 = new Thread(
//            new Runnable() {
//                @Override
//                public void run() {
//                    for(int i=0; i<10000; i++){
//                        User user = new User();
//                        user.setUuid(Integer.toString(i));
//                        userBox1.put(user);
//                        System.out.println(("Insert " + (i) + "User"));
//                    }
//                }
//            }
//        );
//
//        Thread thread2 = new Thread(
//            new Runnable() {
//                @Override
//                public void run() {
//                    for(int i=0; i<10000; i++){
//                        User user = new User();
//                        user.setUuid(Integer.toString(i+20000));
//                        userBox1.put(user);
//                        System.out.println(("Insert " + (i + 20000) + "User"));
//                    }
//                }
//            }
//        );
//
//        store.runInTx(thread1);
//        store.runInTx(thread2);
        User user = new User();
        user.setUuid(Integer.toString(1000));
        User user2 = new User();
        user2.setUuid(Integer.toString(1001));
        userBox1.put(user);
        userBox1.put(user2);
        userBox2.getAll();

        InitService initService  = new InitService();
        initService.initService();
        User user000 = UserDAO.getUserById("8888888");
        System.out.println(user000.getLastUpdated());
    }
}
