package org.umiskky.model.dao;

import io.objectbox.Box;
import io.objectbox.exception.UniqueViolationException;
import org.slf4j.Logger;
import org.umiskky.model.DataModelManager;
import org.umiskky.model.entity.Friend;
import org.umiskky.model.entity.Friend_;
import org.umiskky.service.task.InitTask;

import java.time.Instant;
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
    static void resetStatus(){
        InitTask.store.runInTx(()->{
            List<Friend> friendList = friendBox.getAll();
            if(friendList.size() != 0){
                for(Friend friend : friendList){
                    friend.setStatus(false);
                }
                friendBox.put(friendList);
            }
        });
    }

    /**
     * @description The method getFriendById is used to get friend object by id in the database.
     * @param uuid
     * @return org.umiskky.model.entity.Friend
     * @author umiskky
     * @date 2021/4/21-19:33
     */
    static Friend getFriendById(String uuid){
        return friendBox.query().equal(Friend_.uuid, uuid).build().findUnique();
    }

    /**
     * @description The method clearStatus is used to clear status of friend which are timed out.
     * @param timeInterval
     * @return void
     * @author umiskky
     * @date 2021/4/21-16:30
     */
    static void clearStatus(long timeInterval){
        InitTask.store.runInTx(()->{
            List<Friend> friendList = friendBox.getAll();
            if(friendList != null && friendList.size() != 0){
                long now = Instant.now().toEpochMilli();
                for (Friend friend : friendList){
                    if(now - friend.getLastUpdated() > timeInterval){
                        friend.setStatus(Boolean.FALSE);
                    }
                }
                friendBox.put(friendList);
            }
        });
    }

    static void clearStatus(){
        long timeInterval = 300000;
        InitTask.store.runInTx(()->{
            List<Friend> friendList = friendBox.getAll();
            if(friendList != null && friendList.size() != 0){
                long now = Instant.now().toEpochMilli();
                for (Friend friend : friendList){
                    if(now - friend.getLastUpdated() > timeInterval){
                        friend.setStatus(Boolean.FALSE);
                    }
                }
                friendBox.put(friendList);
            }
        });
    }

    /**
     * @description The method setStatus is used to  set friend status to true.
     * @param uuid
     * @return void
     * @author umiskky
     * @date 2021/4/21-16:35
     */
    static void setStatus(String uuid, long timestamp){
        Friend oldFriend = FriendDAO.getFriendById(uuid);
        if(oldFriend != null && !oldFriend.getStatus() && oldFriend.getLastUpdated() < timestamp) {
            InitTask.store.runInTx(() -> {
                oldFriend.setStatus(Boolean.TRUE);
                oldFriend.setLastUpdated(timestamp);
                friendBox.put(oldFriend);
            });
        }
    }

    /**
     * @description The method putFriend is used to put a friend object to the database.
     * @param friend
     * @return void
     * @author umiskky
     * @date 2021/4/21-19:35
     */
    static void putFriend(Friend friend){
        InitTask.store.runInTx(()->{
            try {
                friendBox.put(friend);
            } catch (UniqueViolationException e){
                log.debug(e.getMessage());
                friendBox.remove(FriendDAO.getFriendById(friend.getUuid()));
                friendBox.put(friend);
            }
        });
    }

    /**
     * @description The method putFriends is used to put multiple friends to the database.
     * @param friends
     * @return void
     * @author umiskky
     * @date 2021/4/21-19:36
     */
    static void putFriends(List<Friend> friends){
        InitTask.store.runInTx(()->{
            for(Friend friend : friends){
                FriendDAO.putFriend(friend);
            }
        });
    }

    /**
     * @description The method getAllFriends is used to get all friends.
     * @param
     * @return java.util.List<org.umiskky.model.entity.Friend>
     * @author umiskky
     * @date 2021/4/22-9:17
     */
    static List<Friend> getAllFriends(){
        return friendBox.query().build().find();
    }
}
