package org.umiskky.model.dao;

import io.objectbox.Box;
import org.slf4j.Logger;
import org.umiskky.model.DataModelManager;
import org.umiskky.model.entity.User;
import org.umiskky.service.task.InitTask;

import java.util.List;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public interface UserDAO {
    Logger log = org.slf4j.LoggerFactory.getLogger(DataModelManager.class);
    Box<User> userBox = InitTask.store.boxFor(User.class);
    /**
     * @description The method removeAll is used to remove all objects in User Entity.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/19-19:18
     */
    public static void removeAll(){
        userBox.removeAll();
        log.info("Database Handle: Remove all users!");
    };

    /**
     * @description The method resetStatus is used to set all User objects status to false.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/19-19:35
     */
    public static void resetStatus(){
        List<User> userList = userBox.getAll();
        if(userList.size() != 0){
            for(User user : userList){
                user.setStatus(false);
            }
            userBox.put(userList);
        }
    }


}
