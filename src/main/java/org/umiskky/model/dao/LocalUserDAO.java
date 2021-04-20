package org.umiskky.model.dao;

import io.objectbox.Box;
import org.slf4j.Logger;
import org.umiskky.model.DataModelManager;
import org.umiskky.model.entity.LocalUser;
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
        if(localUserBox.getAll().size() == 1){
            LocalUser localUser = localUserBox.getAll().get(0);
            return localUser;
        }
        return null;
    }

    /**
     * @description The method putLocalUser is used to put a LocalUser object to the database.
     * @param localUser
     * @return void
     * @author umiskky
     * @date 2021/4/19-23:44
     */
    static void putLocalUser(LocalUser localUser){
        localUserBox.put(localUser);
    }

}