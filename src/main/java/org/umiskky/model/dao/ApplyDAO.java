package org.umiskky.model.dao;

import io.objectbox.Box;
import io.objectbox.exception.UniqueViolationException;
import org.slf4j.Logger;
import org.umiskky.model.DataModelManager;
import org.umiskky.model.entity.Apply;
import org.umiskky.model.entity.Apply_;
import org.umiskky.service.task.InitTask;

import java.util.List;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/21
 */
public interface ApplyDAO {
    Logger log = org.slf4j.LoggerFactory.getLogger(DataModelManager.class);
    Box<Apply> applyBox = InitTask.store.boxFor(Apply.class);

    /**
     * @description The method putApply is used to put an apply object to the database.
     * @param apply
     * @return void
     * @author umiskky
     * @date 2021/4/21-19:01
     */
    static void putApply(Apply apply){
        InitTask.store.runInTx(()->{
            try {
                applyBox.put(apply);
            } catch (UniqueViolationException e){
                log.debug(e.getMessage());
                applyBox.remove(apply);
                applyBox.put(apply);
            }
        });
    }

    /**
     * @description The method putApplys is used to put multiple apply objects to the database.
     * @param applyList
     * @return void
     * @author umiskky
     * @date 2021/4/21-19:02
     */
    static void putApplys(List<Apply> applyList){
        InitTask.store.runInTx(()->{
            for(Apply apply : applyList){
                ApplyDAO.putApply(apply);
            }
        });
    }

    /**
     * @description The method getApply is used to find Apply object in database.
     * @param apply
     * @return org.umiskky.model.entity.Apply
     * @author umiskky
     * @date 2021/4/21-19:23
     */
    static Apply getApply(Apply apply){
        Apply applyObject = applyBox.query()
                .equal(Apply_.uuid, apply.getUuid())
                .equal(Apply_.groupUuid, apply.getGroupUuid())
                .equal(Apply_.isGroup, apply.getIsGroup()).build().findUnique();
        return applyObject;
    }

    /**
     * @description The method removeApply is used to remove apply that is out of work.
     * @param apply
     * @return void
     * @author umiskky
     * @date 2021/4/21-19:00
     */
    static void removeApply(Apply apply){
        InitTask.store.runInTx(()->{
            if(apply.getId() == 0){
                Apply applyObject = ApplyDAO.getApply(apply);
                applyBox.remove(applyObject);
            }else{
                applyBox.remove(apply);
            }
        });
    }
}
