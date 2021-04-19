package org.umiskky.model.dao;

import io.objectbox.Box;
import org.slf4j.Logger;
import org.umiskky.model.DataModelManager;
import org.umiskky.model.entity.Friend;
import org.umiskky.service.task.InitTask;

import java.util.List;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public interface FriendDAO {
    Logger log = org.slf4j.LoggerFactory.getLogger(DataModelManager.class);
    Box<Friend> friendBox = InitTask.store.boxFor(Friend.class);

    /**
     * @description The method resetStatus is used to set all Friend objects status to false.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/19-19:35
     */
    public static void resetStatus(){
        List<Friend> friendList = friendBox.getAll();
        if(friendList.size() != 0){
            for(Friend friend : friendList){
                friend.setStatus(false);
            }
            friendBox.put(friendList);
        }
    }
}
