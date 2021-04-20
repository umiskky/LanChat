package org.umiskky.model.dao;

import io.objectbox.Box;
import org.slf4j.Logger;
import org.umiskky.model.DataModelManager;
import org.umiskky.model.entity.GroupMember;
import org.umiskky.service.task.InitTask;

import java.util.List;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public interface GroupMemberDAO {
    Logger log = org.slf4j.LoggerFactory.getLogger(DataModelManager.class);
    Box<GroupMember> groupMemberBox = InitTask.store.boxFor(GroupMember.class);

    /**
     * @description The method resetStatus is used to set all GroupMember objects status to false.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/19-20:02
     */
    static void resetStatus(){
        List<GroupMember> groupMemberList = groupMemberBox.getAll();
        if(groupMemberList.size() != 0){
            for(GroupMember groupMember : groupMemberList){
                groupMember.setStatus(false);
            }
            groupMemberBox.put(groupMemberList);
        }
    }
}
