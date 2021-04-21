package org.umiskky.model.dao;

import io.objectbox.Box;
import org.slf4j.Logger;
import org.umiskky.model.DataModelManager;
import org.umiskky.model.entity.GroupMember;
import org.umiskky.model.entity.GroupMember_;
import org.umiskky.service.task.InitTask;

import java.time.Instant;
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
        InitTask.store.runInTx(()->{
            List<GroupMember> groupMemberList = groupMemberBox.getAll();
            if(groupMemberList.size() != 0){
                for(GroupMember groupMember : groupMemberList){
                    groupMember.setStatus(false);
                }
                groupMemberBox.put(groupMemberList);
            }
        });
    }

    /**
     * @description The method clearStatus is used to clear status of group members which are timed out.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/21-15:37
     */
    static void clearStatus(long timeInterval){
        InitTask.store.runInTx(()->{
            List<GroupMember> groupMemberList = groupMemberBox.getAll();
            if(groupMemberList != null && groupMemberList.size() != 0){
                long now = Instant.now().toEpochMilli();
                for (GroupMember groupMember : groupMemberList){
                    if(now - groupMember.getLastUpdated() > timeInterval){
                        groupMember.setStatus(Boolean.FALSE);
                    }
                }
                groupMemberBox.put(groupMemberList);
            }
        });
    }

    static void clearStatus(){
        long timeInterval = 300000;
        InitTask.store.runInTx(()->{
            List<GroupMember> groupMemberList = groupMemberBox.getAll();
            if(groupMemberList != null && groupMemberList.size() != 0){
                long now = Instant.now().toEpochMilli();
                for (GroupMember groupMember : groupMemberList){
                    if(now - groupMember.getLastUpdated() > timeInterval){
                        groupMember.setStatus(Boolean.FALSE);
                    }
                }
                groupMemberBox.put(groupMemberList);
            }
        });
    }

    /**
     * @description The method getGroupMemberById is used to get group member by id.
     * @param uuid
     * @return org.umiskky.model.entity.GroupMember
     * @author umiskky
     * @date 2021/4/21-15:37
     */
    static GroupMember getGroupMemberById(String uuid){
        return groupMemberBox.query().equal(GroupMember_.uuid, uuid).build().findUnique();
    }

    /**
     * @description The method setStatus is used to set group member status to true.
     * @param uuid
     * @return void
     * @author umiskky
     * @date 2021/4/21-15:38
     */
    static void setStatus(String uuid, long timestamp){
        GroupMember oldGroupMember = GroupMemberDAO.getGroupMemberById(uuid);
        if(oldGroupMember != null && !oldGroupMember.getStatus() && oldGroupMember.getLastUpdated() < timestamp){
            InitTask.store.runInTx(()->{
                oldGroupMember.setStatus(Boolean.TRUE);
                oldGroupMember.setLastUpdated(timestamp);
            });
            groupMemberBox.put(oldGroupMember);
        }
    }
}
