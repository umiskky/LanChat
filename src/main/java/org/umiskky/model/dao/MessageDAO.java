package org.umiskky.model.dao;

import io.objectbox.Box;
import io.objectbox.exception.UniqueViolationException;
import org.slf4j.Logger;
import org.umiskky.model.DataModelManager;
import org.umiskky.model.entity.Message;
import org.umiskky.model.entity.Message_;
import org.umiskky.service.task.InitTask;

import java.util.List;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public interface MessageDAO {
    Logger log = org.slf4j.LoggerFactory.getLogger(DataModelManager.class);
    Box<Message> messageBox = InitTask.store.boxFor(Message.class);

    /**
     * @description The method putMessage is used to put a message to the database.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/22-9:19
     */
    static void putMessage(Message message){
        InitTask.store.runInTx(()->{
            try {
                messageBox.put(message);
            } catch (UniqueViolationException e){
                log.debug(e.getMessage());
            }
        });
    }

    /**
     * @description The method getMessages is used to get messages belong to two friends or group.
     * @param uuid0
     * @param uuid1
     * @return java.util.List<org.umiskky.model.entity.Message>
     * @author umiskky
     * @date 2021/4/22-12:50
     */
    static List<Message> getMessages(String uuid0, String uuid1){
        return messageBox.query()
                .equal(Message_.fromUuid, uuid0).equal(Message_.toUuid, uuid1)
                .or()
                .equal(Message_.fromUuid, uuid1).equal(Message_.toUuid, uuid0)
                .order(Message_.sendTime).build().find();
    }
}
