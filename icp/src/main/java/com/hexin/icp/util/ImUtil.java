package com.hexin.icp.util;

import java.util.List;




import org.springframework.util.CollectionUtils;

import com.hexin.icp.app.exceptions.ICPIMException;
import com.hexin.icp.app.im.IMGroup;
import com.hexin.icp.app.im.rong.IMCoummunication;
import com.hexin.icp.bean.Group;

/**
 * 类ImUtil.java的实现描述：TODO 同步IM聊天群、成员相关信息
 * 
 * @author Administrator 2015年7月30日 下午2:33:00
 */
public class ImUtil {

    /**
     * 创建群
     * 
     * @param newOrgGroupId
     * @param newOrgGroupName
     * @throws ICPIMException
     */
    public static void createGroup(Integer groupId, String groupName) throws ICPIMException {
        IMGroup imGroup = new IMGroup();
        imGroup.setGroupID(groupId.toString());
        imGroup.setGroupName(groupName);

        IMCoummunication.createGroup(imGroup);

    }

    /**
     * 批量加入到im群聊列表
     * 
     * @param groupList
     * @param appUserId
     * @throws ICPIMException 
     */
    public static void batchJoinToGroup(List<Group> groupList, Integer appUserId) throws ICPIMException {
        if(CollectionUtils.isEmpty(groupList)){
            return ;
        }
        
        for(Group group : groupList){
            
            IMGroup imGroup = new IMGroup();
            imGroup.setGroupID(group.getGroupId().toString());
            imGroup.setGroupName(group.getGroupName());
            
            IMCoummunication.joinGroup(imGroup, appUserId.toString());
        }
        
    }

    /**
     * 加入到im群聊列表
     * 
     * @param group
     * @param appUserId
     * @throws ICPIMException 
     */
    public static void joinToGroup(Group group, Integer appUserId) throws ICPIMException {
        if(group == null){
            return ;
        }
        
        IMGroup imGroup = new IMGroup();
        imGroup.setGroupID(group.getGroupId().toString());
        imGroup.setGroupName(group.getGroupName());
        
        IMCoummunication.joinGroup(imGroup, appUserId.toString());
    }

}
