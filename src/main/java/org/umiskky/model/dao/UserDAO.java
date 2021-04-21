package org.umiskky.model.dao;

import io.objectbox.Box;
import io.objectbox.exception.UniqueViolationException;
import org.slf4j.Logger;
import org.umiskky.model.DataModelManager;
import org.umiskky.model.entity.User;
import org.umiskky.model.entity.User_;
import org.umiskky.service.task.InitTask;

import java.time.Instant;
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
    static void removeAll(){
        InitTask.store.runInTx(()->{
            userBox.removeAll();
            log.info("Database Handle: Remove all users!");
        });
    };

    /**
     * @description The method resetStatus is used to set all User objects status to false.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/19-19:35
     */
    static void resetStatus(){
        InitTask.store.runInTx(()->{
            List<User> userList = userBox.getAll();
            if(userList.size() != 0){
                for(User user : userList){
                    user.setStatus(false);
                }
                userBox.put(userList);
            }
        });
    }

    /**
     * @description The method getUserById is used to find User by uuid.
     * @param uuid
     * @return org.umiskky.model.entity.User
     * @author umiskky
     * @date 2021/4/21-10:31
     */
    static User getUserById(String uuid){
        return userBox.query().equal(User_.uuid, uuid).build().findUnique();
    }

    /**
     * @description The method putUser is used to update user in user entity.
     * @param user
     * @return void
     * @author umiskky
     * @date 2021/4/21-12:03
     */
    static void putUser(User user){
        InitTask.store.runInTx(()->{
            try {
                userBox.put(user);
            } catch (UniqueViolationException e){
                log.debug(e.getMessage());
                userBox.remove(UserDAO.getUserById(user.getUuid()));
                userBox.put(user);
            }
        });
    }

    /**
     * @description The method putUsers is used to put multiple users in user entity.
     * @param users
     * @return void
     * @author umiskky
     * @date 2021/4/21-12:04
     */
    static void putUsers(List<User> users){
        InitTask.store.runInTx(()->{
            for(User user : users){
                UserDAO.putUser(user);
            }
        });
    }

    /**
     * @description The method clearStatus is used to clear status of users which are timed out.
     * @param timeInterval
     * @return void
     * @author umiskky
     * @date 2021/4/21-16:13
     */
    static void clearStatus(long timeInterval){
        InitTask.store.runInTx(()->{
            List<User> userList = userBox.getAll();
            if(userList != null && userList.size() != 0){
                long now = Instant.now().toEpochMilli();
                for (User user : userList){
                    if(now - user.getLastUpdated() > timeInterval){
                        user.setStatus(Boolean.FALSE);
                    }
                }
                userBox.put(userList);
            }
        });
    }

    static void clearStatus(){
        long timeInterval = 300000;
        InitTask.store.runInTx(()->{
            List<User> userList = userBox.getAll();
            if(userList != null && userList.size() != 0){
                long now = Instant.now().toEpochMilli();
                for (User user : userList){
                    if(now - user.getLastUpdated() > timeInterval){
                        user.setStatus(Boolean.FALSE);
                    }
                }
                userBox.put(userList);
            }
        });
    }

    /**
     * @description The method updateUser is used to update user in user entity.
     * @param user
     * @return void
     * @author umiskky
     * @date 2021/4/21-14:34
     */
    static void updateUser(User user){
        User oldUser = UserDAO.getUserById(user.getUuid());
        if(oldUser == null){
            InitTask.store.runInTx(()->{
                userBox.put(user);
            });
        }else if(oldUser.getLastUpdated() == 0 || oldUser.getLastUpdated() < user.getLastUpdated()){
            UserDAO.putUser(user);
        }
    }

    /**
     * @description The method setStatus is used to set user status to true.
     * @param uuid
     * @return int
     * @author umiskky
     * @date 2021/4/21-16:16
     */
    static int setStatus(String uuid, long timestamp){
        User oldUser = UserDAO.getUserById(uuid);
        if(oldUser != null && !oldUser.getStatus() && oldUser.getLastUpdated() < timestamp){
            InitTask.store.runInTx(()->{
                oldUser.setStatus(Boolean.TRUE);
                oldUser.setLastUpdated(timestamp);
                userBox.put(oldUser);
            });
            return 1;
        }else if(oldUser == null){
            return -1;
        }
        return 0;
    }

}
