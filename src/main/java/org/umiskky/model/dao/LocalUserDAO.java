package org.umiskky.model.dao;

import io.objectbox.Box;
import io.objectbox.exception.UniqueViolationException;
import org.slf4j.Logger;
import org.umiskky.model.DataModelManager;
import org.umiskky.model.entity.LocalUser;
import org.umiskky.model.entity.LocalUser_;
import org.umiskky.service.task.InitTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public interface LocalUserDAO {
    Logger log = org.slf4j.LoggerFactory.getLogger(DataModelManager.class);
    Box<LocalUser> localUserBox = InitTask.store.boxFor(LocalUser.class);

    /**
     * @description The method getLocalUser is used to get LocalUser.
     * @param
     * @return org.umiskky.model.entity.LocalUser
     * @author umiskky
     * @date 2021/4/19-23:35
     */
    static LocalUser getLocalUser(){
        return localUserBox.query().build().findUnique();
    }

    static LocalUser getLocalUserById(String uuid){
        return localUserBox.query().equal(LocalUser_.uuid, uuid).build().findUnique();
    }

    /**
     * @description The method putLocalUser is used to put a LocalUser object to the database.
     * @param localUser
     * @return void
     * @author umiskky
     * @date 2021/4/19-23:44
     */
    static void putLocalUser(LocalUser localUser){
        InitTask.store.runInTx(()->{
            try {
                localUserBox.put(localUser);
            } catch (UniqueViolationException e){
                log.debug(e.getMessage());
                localUserBox.remove(LocalUserDAO.getLocalUserById(localUser.getUuid()));
                localUserBox.put(localUser);
            }
        });
    }

}
