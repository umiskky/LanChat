package org.umiskky.model.dao;

import io.objectbox.Box;
import io.objectbox.exception.UniqueViolationException;
import org.slf4j.Logger;
import org.umiskky.model.DataModelManager;
import org.umiskky.model.entity.Group;
import org.umiskky.model.entity.Group_;
import org.umiskky.service.task.InitTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public interface GroupDAO {
    Logger log = org.slf4j.LoggerFactory.getLogger(DataModelManager.class);
    Box<Group> groupBox = InitTask.store.boxFor(Group.class);

    /**
     * @description The method getGroupById is used to find group object by id in the database.
     * @param uuid
     * @return org.umiskky.model.entity.Group
     * @author umiskky
     * @date 2021/4/21-19:46
     */
    static Group getGroupById(String uuid){
        return groupBox.query().equal(Group_.uuid, uuid).build().findUnique();
    }

    /**
     * @description The method putGroup is used to put group object to the database.
     * @param group
     * @return void
     * @author umiskky
     * @date 2021/4/21-19:50
     */
    static void putGroup(Group group){
        InitTask.store.runInTx(()->{
            try {
                groupBox.put(group);
            } catch (UniqueViolationException e){
                log.debug(e.getMessage());
                groupBox.remove(GroupDAO.getGroupById(group.getUuid()));
                groupBox.put(group);
            }
        });
    }
}
