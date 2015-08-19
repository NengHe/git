package com.hexin.icp.dao;

import java.util.List;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.Group;
import com.hexin.icp.bean.OrgMember;

public interface OrgMemberDao {

    /**
     * 为系统管理员查询所有成员
     * 
     * @param colUserMobile
     * @return
     */
    public List<OrgMember> queryOrgMembers4Sys(String colUserMobile);

    /**
     * 为机构管理员查询成员
     * 
     * @param orgId
     * @param colUserMobile
     * @return
     */
    public List<OrgMember> queryOrgMembers4Org(Integer orgId, String colUserMobile);

    /**
     * 添加t_app_user记录
     * 
     * @param colUserName
     * @param colUserMobile
     * @param colUserCompany
     * @param colUserEmail
     * @param colUserAddress
     * @param colReceiveMsgFlag
     * @param createBy
     * @return appUserId
     */
    public Integer insertAppUser(String colUserName, String colUserMobile, String colUserCompany,
            String colUserIndustry, String colUserJob, String colUserEmail, String colUserAddress,
            Integer colReceiveMsgFlag, Integer colFriendInvite, Integer createBy);

    /**
     * 添加成员记录
     * 
     * @param params
     */
    public int insetOrgMembers(Integer orgId, Integer appUserId, Integer positionId, Integer colShowDetailInner,
            Integer colShowDetailOutter);

    /**
     * 批量添加成员记录
     * 
     * @param params
     */
    public void batchInsetOrgMembers(List<Object[]> params);

    /**
     * 修改t_app_user记录
     * 
     * @param userId
     * @param colUserName
     * @param colUserMobile
     * @param colUserCompany
     * @param colUserEmail
     * @param colUserAddress
     * @param colReceiveMsgFlag
     * @param updateBy
     * @return
     */
    public int updateAppUser(Integer userId, String colUserName, String colUserMobile, String colUserCompany,
            String colUserIndustry, String colUserJob, String colUserEmail, String colUserAddress,
            Integer colReceiveMsgFlag, Integer colFriendInvite, Integer updateBy);

    /**
     * 删除t_org_members记录
     * 
     * @param userId
     */
    // public int deleteOrgMembersByUserId(Integer userId);

    /**
     * 根据机构id、用户id查询机构成员记录
     * 
     * @param reqOrgId
     * @param appUserId
     * @return
     */
    public OrgMember getOrgMembersByOrgIdAndUserId(Integer reqOrgId, Integer appUserId);

    /**
     * 更新机构用户
     * 
     * @param orgMemInternalId
     * @param positionId
     * @param colShowDetailInner
     * @param colShowDetailOutter
     * @return
     */
    public int updateOrgMembers(Integer orgMemInternalId, Integer positionId, Integer colShowDetailInner,
            Integer colShowDetailOutter);

    /**
     * 批量更新成员
     * 
     * @param batchUpdateParams
     */
    public void batchUpdateOrgMembers(List<Object[]> batchUpdateParams);

    /**
     * 根据机构id、用户id批量软删除t_org_members关联记录
     * 
     * @param batchDeleteParams
     */
    public void batchSoftDeleteOrgMembersByOrgIdAndUserId(List<Object[]> batchDeleteParams);

    /**
     * 查询用户机构列表
     * 
     * @param userId
     * @return
     */
    public List<OrgMember> queryOrgMembers(Integer userId);

    /**
     * 根据机构id查询机构群id
     * 
     * @param orgId
     * @return
     */
    public Group getGroupIdByOrgId(Integer orgId);

    /**
     * 批量创建机构聊天群成员关联记录
     * 
     * @param groupMemberParams
     */
    public void batchInsertOrgGroupMembers(List<Object[]> groupMemberParams);

    /**
     * 创建机构聊天群成员关联记录
     * 
     * @param groupMemberParams
     */
    public int insertOrgGroupMember(Integer groupId, Integer appUserId);

    /**
     * 为机构管理员查询成员
     * 
     * @param pageCondition
     * @param orgId
     * @param colUserMobile
     * @return
     */
    public Page<OrgMember> queryOrgMembersPage4Org(PageCondition pageCondition, Integer orgId, String colUserMobile);

}
