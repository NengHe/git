package com.hexin.icp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.app.exceptions.ICPIMException;
import com.hexin.icp.bean.Group;
import com.hexin.icp.bean.OrgMember;
import com.hexin.icp.bean.OrgPosition;
import com.hexin.icp.dao.OrgMemberDao;
import com.hexin.icp.service.OrgMemberService;
import com.hexin.icp.util.ImUtil;

@Service
@Transactional(value = "transactionManager")
public class OrgMemberServiceImpl implements OrgMemberService {

    private static final Logger logger = LoggerFactory.getLogger(OrgMemberServiceImpl.class);

    @Autowired
    private OrgMemberDao        dao;

    @Override
    public List<OrgMember> queryOrgMembers(Integer adminUserId, String adminUserType, Integer orgId,
            String colUserMobile) {

        List<OrgMember> list = null;

        if ("1".equals(adminUserType)) { // 机构管理员
            list = dao.queryOrgMembers4Org(orgId, colUserMobile);
        } else if ("2".equals(adminUserType)) { // 系统管理员
            list = dao.queryOrgMembers4Sys(colUserMobile);
        }

        List<OrgMember> newList = format4queryOrgMembers(list);

        return newList;
    }

    private List<OrgMember> format4queryOrgMembers(List<OrgMember> list) {

        List<OrgMember> result = new ArrayList<OrgMember>();
        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();

        if (CollectionUtils.isEmpty(list)) {
            return result;
        }

        for (OrgMember dto : list) {
            Integer userId = dto.getUserId();
            Boolean flg = map.get(userId);
            if (BooleanUtils.isTrue(flg)) {
                continue;
            }

            String colUserName = dto.getColUserName();
            String colUserMobile = dto.getColUserMobile();
            String colUserEmail = dto.getColUserEmail();
            String colUserAddress = dto.getColUserAddress();
            String colUserCompany = dto.getColUserCompany();
            String colUserJob = dto.getColUserJob();
            String colReceiveMsgFlag = dto.getColReceiveMsgFlag();
            String colFriendInvite = dto.getColFriendInvite();

            Integer orgId = dto.getOrgId();
            String colOrgCode = dto.getColOrgCode();
            String colOrgName = dto.getColOrgName();

            Integer orgMemInternalId = dto.getOrgMemInternalId();
            Integer colShowDetailInner = dto.getColShowDetailInner();
            Integer colShowDetailOutter = dto.getColShowDetailOutter();
            Integer colOrgUserStatus = dto.getColOrgUserStatus();

            Integer positionId = dto.getPositionId();
            String colPosName = dto.getColPosName();

            String colUserIndustry = dto.getColUserIndustry();

            OrgMember orgMember = new OrgMember();
            orgMember.setUserId(userId);
            orgMember.setColUserName(colUserName);
            orgMember.setColUserMobile(colUserMobile);
            orgMember.setColUserEmail(colUserEmail);
            orgMember.setColUserAddress(colUserAddress);
            orgMember.setColUserCompany(colUserCompany);
            orgMember.setColUserJob(colUserJob);
            orgMember.setColReceiveMsgFlag(colReceiveMsgFlag);
            orgMember.setColFriendInvite(colFriendInvite);
            // orgMember.setOrgId(orgId);
            // orgMember.setColOrgCode(colOrgCode);
            // orgMember.setColOrgName(colOrgName);
            orgMember.setOrgMemInternalId(orgMemInternalId);
            orgMember.setColShowDetailInner(colShowDetailInner);
            orgMember.setColShowDetailOutter(colShowDetailOutter);
            orgMember.setColOrgUserStatus(colOrgUserStatus);
            // orgMember.setPositionId(positionId);
            // orgMember.setColPosName(colPosName);
            orgMember.setColUserIndustry(colUserIndustry);

            List<OrgPosition> orgPositions = new ArrayList<OrgPosition>();
            for (OrgMember inDto : list) {
                Integer inUserId = inDto.getUserId();
                Integer inOrgId = inDto.getOrgId();
                Integer inPositionId = inDto.getPositionId();

                if (orgId == null || positionId == null || inOrgId == null || inPositionId == null) {
                    continue;
                }

                boolean userIdEqual = userId.equals(inUserId);
                boolean orgIdEqual = orgId.equals(inOrgId);
                boolean posIdEqual = positionId.equals(inPositionId);
                // if (userIdEqual && (!orgIdEqual || !posIdEqual)) {
                // if (userIdEqual && (!posIdEqual)) {
                if (userIdEqual) {
                    String inColOrgCode = inDto.getColOrgCode();
                    String inColOrgName = inDto.getColOrgName();
                    String inColPosName = inDto.getColPosName();

                    OrgPosition orgPos = new OrgPosition();
                    orgPos.setOrgId(inOrgId);
                    orgPos.setColOrgCode(inColOrgCode);
                    orgPos.setColOrgName(inColOrgName);
                    orgPos.setPositionId(inPositionId);
                    orgPos.setColPosName(inColPosName);
                    orgPositions.add(orgPos);
                }
            }
            orgMember.setOrgPositions(orgPositions);

            result.add(orgMember);
            map.put(userId, true); // 避免重复
        }

        return result;
    }

    @Override
    public int createOrgMember(Integer[] orgIdArr, Integer[] positionIdArr, String colUserName, String colUserMobile,
            String colUserCompany, String colUserIndustry, String colUserJob, String colUserEmail,
            String colUserAddress, Integer colReceiveMsgFlag, Integer colFriendInvite, Integer colShowDetailInner,
            Integer colShowDetailOutter, Integer createBy) throws ICPIMException {

        Integer appUserId = null;
        List<Object[]> orgMemberparams = new ArrayList<Object[]>();
        List<Object[]> groupMemberParams = new ArrayList<Object[]>();
        List<Group> groupList = new ArrayList<Group>();

        // 添加t_app_user记录
        appUserId = dao.insertAppUser(colUserName, colUserMobile, colUserCompany, colUserIndustry, colUserJob,
                colUserEmail, colUserAddress, colReceiveMsgFlag, colFriendInvite, createBy);

        // 批量添加t_org_members记录
        if (orgIdArr != null && orgIdArr.length > 0 && positionIdArr != null && positionIdArr.length > 0
                && orgIdArr.length == positionIdArr.length) {
            for (int i = 0; i < orgIdArr.length; i++) {
                Integer orgId = orgIdArr[i];

                Object[] orgMemberParam = new Object[] { orgId, appUserId, positionIdArr[i], colShowDetailInner,
                        colShowDetailOutter };
                // 机构成员批量参数
                orgMemberparams.add(orgMemberParam);

                // 根据机构id查询对应的groupId
                Group group = dao.getGroupIdByOrgId(orgId);
                if (group != null) {
                    Integer groupId = group.getGroupId();
                    Object[] groupMemberParam = new Object[] { groupId, appUserId };

                    // 聊天群成员批量参数
                    groupMemberParams.add(groupMemberParam);

                    // im聊天群批量参数
                    groupList.add(group);
                }
            }
        }

        // 创建机构成员关联记录
        if (!CollectionUtils.isEmpty(orgMemberparams)) {
            dao.batchInsetOrgMembers(orgMemberparams);
        }

        // 批量创建机构聊天群成员关联记录
        if (!CollectionUtils.isEmpty(groupMemberParams)) {
            dao.batchInsertOrgGroupMembers(groupMemberParams);
        }

        // 将当前成员加入到IM聊天群当中
        if (!CollectionUtils.isEmpty(groupList)) {
            ImUtil.batchJoinToGroup(groupList, appUserId);
        }

        return 1;
    }

    @Override
    public int updateOrgMember(Integer userId, Integer[] orgIdArr, Integer[] positionIdArr, String colUserName,
            String colUserMobile, String colUserCompany, String colUserIndustry, String colUserJob,
            String colUserEmail, String colUserAddress, Integer colReceiveMsgFlag, Integer colFriendInvite,
            Integer colShowDetailInner, Integer colShowDetailOutter, Integer updateBy) {

        List<OrgMember> joinedList = null;
        int affectedUserRows;
        List<Object[]> batchInsertParams;
        List<Object[]> batchUpdateParams;
        List<Object[]> batchDeleteParams;

        // 修改t_app_user记录
        affectedUserRows = dao.updateAppUser(userId, colUserName, colUserMobile, colUserCompany, colUserIndustry,
                colUserJob, colUserEmail, colUserAddress, colReceiveMsgFlag, colFriendInvite, updateBy);

        // 批量添加t_org_members记录
        batchInsertParams = new ArrayList<Object[]>();
        batchUpdateParams = new ArrayList<Object[]>();
        if (orgIdArr != null && orgIdArr.length > 0 && positionIdArr != null && positionIdArr.length > 0
                && orgIdArr.length == positionIdArr.length) {
            for (int i = 0; i < orgIdArr.length; i++) {
                Integer orgId = orgIdArr[i];
                // 验证该用户尚未加入该机构
                OrgMember om = dao.getOrgMembersByOrgIdAndUserId(orgId, userId);
                if (om != null) {
                    Object[] param = new Object[] { positionIdArr[i], colShowDetailInner, colShowDetailOutter, orgId,
                            userId };
                    batchUpdateParams.add(param);
                } else {
                    Object[] param = new Object[] { orgId, userId, positionIdArr[i], colShowDetailInner,
                            colShowDetailOutter };
                    batchInsertParams.add(param);
                }
            }
        }

        dao.batchInsetOrgMembers(batchInsertParams);

        dao.batchUpdateOrgMembers(batchUpdateParams);

        // 查找用户已经加入的机构
        joinedList = dao.queryOrgMembers(userId);
        // 删除多余的t_org_members记录
        if (!CollectionUtils.isEmpty(joinedList)) {
            batchDeleteParams = new ArrayList<Object[]>();

            for (OrgMember om : joinedList) {
                Integer joinedOrgId = om.getOrgId();
                boolean toBeJoined = false;

                if (orgIdArr != null && orgIdArr.length > 0) {
                    for (int i = 0; i < orgIdArr.length; i++) {
                        Integer toJoinOrgId = orgIdArr[i];
                        if (joinedOrgId.equals(toJoinOrgId)) {
                            toBeJoined = true;
                            break;
                        }
                    }
                }

                // 当用户已经加入的机构不在orgIdArr中，则需要删除
                if (!toBeJoined) {
                    Object[] param = new Object[] { joinedOrgId, userId };
                    batchDeleteParams.add(param);
                }
            }

            // 根据机构id、用户id批量删除关联记录
            dao.batchSoftDeleteOrgMembersByOrgIdAndUserId(batchDeleteParams);
        }

        return 1;
    }

    @Override
    public Page<OrgMember> queryMyOrgMembers(PageCondition pageCondition, Integer orgId, String colUserMobile) {

        Page<OrgMember> page = dao.queryOrgMembersPage4Org(pageCondition, orgId, colUserMobile);

        return page;
    }

    @Override
    public int createMyOrgMember(Integer orgId, Integer positionId, String colUserName, String colUserMobile,
            String colUserCompany, String colUserIndustry, String colUserJob, String colUserEmail,
            String colUserAddress, Integer colReceiveMsgFlag, Integer colFriendInvite, Integer colShowDetailInner,
            Integer colShowDetailOutter, Integer createBy) throws ICPIMException {

        int affectedOrgMemberRows;
        int affectedGroupMemberRows;
        Integer appUserId = null;
        Group group = null;
        Integer groupId = null;

        // 添加t_app_user记录
        appUserId = dao.insertAppUser(colUserName, colUserMobile, colUserCompany, colUserIndustry, colUserJob,
                colUserEmail, colUserAddress, colReceiveMsgFlag, colFriendInvite, createBy);

        // 添加t_org_members记录
        affectedOrgMemberRows = dao.insetOrgMembers(orgId, appUserId, positionId, colShowDetailInner,
                colShowDetailOutter);

        // 根据机构id查询对应的groupId
        group = dao.getGroupIdByOrgId(orgId);
        if (group != null) {
            groupId = group.getGroupId();

            // 添加机构聊天群成员管来呢记录
            affectedGroupMemberRows = dao.insertOrgGroupMember(groupId, appUserId);

            // 将当前成员加入到IM聊天群当中
            ImUtil.joinToGroup(group, appUserId);
        }

        return affectedOrgMemberRows;
    }

    @Override
    public int updateMyOrgMember(Integer orgMemInternalId, Integer userId, Integer orgId, Integer positionId,
            String colUserName, String colUserMobile, String colUserCompany, String colUserIndustry, String colUserJob,
            String colUserEmail, String colUserAddress, Integer colReceiveMsgFlag, Integer colFriendInvite,
            Integer colShowDetailInner, Integer colShowDetailOutter, Integer updateBy) {

        int affectedUserRows;
        int affectedMemRows;

        // 修改t_app_user记录
        affectedUserRows = dao.updateAppUser(userId, colUserName, colUserMobile, colUserCompany, colUserIndustry,
                colUserJob, colUserEmail, colUserAddress, colReceiveMsgFlag, colFriendInvite, updateBy);

        affectedMemRows = dao.updateOrgMembers(orgMemInternalId, positionId, colShowDetailInner, colShowDetailOutter);

        return 1;
    }

    @Override
    public int addOrgMember(Integer userId, Integer[] orgIdArr, Integer[] positionIdArr, Integer colShowDetailInner,
            Integer colShowDetailOutter, Integer createBy) throws ICPIMException {

        List<Object[]> orgMemberparams = new ArrayList<Object[]>();
        List<Object[]> groupMemberParams = new ArrayList<Object[]>();
        List<Group> groupList = new ArrayList<Group>();

        // 批量添加t_org_members记录
        if (orgIdArr != null && orgIdArr.length > 0 && positionIdArr != null && positionIdArr.length > 0
                && orgIdArr.length == positionIdArr.length) {
            for (int i = 0; i < orgIdArr.length; i++) {
                Integer orgId = orgIdArr[i];

                Object[] orgMemberParam = new Object[] { orgId, userId, positionIdArr[i], colShowDetailInner,
                        colShowDetailOutter };
                // 机构成员批量参数
                orgMemberparams.add(orgMemberParam);

                // 根据机构id查询对应的groupId
                Group group = dao.getGroupIdByOrgId(orgId);
                if (group != null) {
                    Integer groupId = group.getGroupId();
                    Object[] groupMemberParam = new Object[] { groupId, userId };

                    // 聊天群成员批量参数
                    groupMemberParams.add(groupMemberParam);

                    // im聊天群批量参数
                    groupList.add(group);
                }

            }
        }

        // 创建机构成员关联记录
        if (!CollectionUtils.isEmpty(orgMemberparams)) {
            dao.batchInsetOrgMembers(orgMemberparams);
        }

        // 批量创建机构聊天群成员关联记录
        if (!CollectionUtils.isEmpty(groupMemberParams)) {
            dao.batchInsertOrgGroupMembers(groupMemberParams);
        }

        // 将当前成员加入到IM聊天群当中
        if (!CollectionUtils.isEmpty(groupList)) {
            ImUtil.batchJoinToGroup(groupList, userId);
        }

        return 1;
    }

    // @Override
    // public int addMyOrgMember(Integer userId, Integer orgId, Integer
    // positionId, Integer colShowDetailInner,
    // Integer colShowDetailOutter, Integer createBy) {
    // int affectedOrgMemberRows;
    //
    // // 添加t_org_members记录
    // affectedOrgMemberRows = dao.insetOrgMembers(orgId, userId, positionId,
    // colShowDetailInner, colShowDetailOutter);
    //
    // return affectedOrgMemberRows;
    // }
}
