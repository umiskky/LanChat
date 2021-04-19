package org.umiskky.model.entity;

import cn.hutool.core.util.IdUtil;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
public class GroupTest {

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
    public void testGroup() {
        Box<Group> groupBox = store.boxFor(Group.class);
        Box<GroupMember> groupMembersBox = store.boxFor(GroupMember.class);


        Group group = new Group();
        group.setUuid(IdUtil.simpleUUID());
        group.setName("group");

        GroupMember groupMember1 = new GroupMember();
        groupMember1.setUuid(IdUtil.simpleUUID());
        groupMember1.setNickname("member1");

        GroupMember groupMember2 = new GroupMember();
        groupMember2.setUuid(IdUtil.simpleUUID());
        groupMember2.setNickname("member2");

        group.groupMembers.add(groupMember1);
        group.groupMembers.add(groupMember2);


        groupBox.put(group);


        GroupMember test = groupMembersBox.query().equal(GroupMember_.uuid, groupMember2.getUuid()).build().findFirst();
        assert test != null;
        log.info(test.toString());
    }
}
