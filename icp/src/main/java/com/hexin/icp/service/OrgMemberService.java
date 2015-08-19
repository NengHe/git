package com.hexin.icp.service;

import java.util.List;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.app.exceptions.ICPIMException;
import com.hexin.icp.bean.OrgMember;

public interface OrgMemberService {

    /**
     * 查询机构成员列表
     * 
     * @param adminUserId
     * @param adminUserType
     * @param Integer orgId,
     * @param colUserMobile
     * @return
     */
    public List<OrgMember> queryOrgMembers(Integer adminUserId, String adminUserType, Integer orgId,
            String colUserMobile);

    /**
     * 创建机构成员
     * 
     * @param orgId
     * @param positionId
     * @param colUserName
     * @param colUserMobile
     * @param colUserCompany
     * @param colUserEmail
     * @param colUserAddress
     * @param colReceiveMsgFlag
     * @param colShowDetailInner
     * @param colShowDetailOutter
     * @return
     * @throws ICPIMException
     */
    public int createOrgMember(Integer[] orgIdArr, Integer[] positionIdArr, String colUserName, String colUserMobile,
            String colUserCompany, String colUserIndustry, String colUserJob, String colUserEmail,
            String colUserAddress, Integer colReceiveMsgFlag, Integer colFriendInvite, Integer colShowDetailInner,
            Integer colShowDetailOutter, Integer createBy) throws ICPIMException;

    /**
     * 修改机构成员
     * 
     * @param orgMemInternalId
     * @param userId
     * @param orgIdArr
     * @param positionIdArr
     * @param colUserName
     * @param colUserMobile
     * @param colUserCompany
     * @param colUserEmail
     * @param colUserAddress
     * @param colReceiveMsgFlag
     * @param colShowDetailInner
     * @param colShowDetailOutter
     * @param updateBy
     * @return
     */
    public int updateOrgMember(Integer userId, Integer[] orgIdArr, Integer[] positionIdArr, String colUserName,
            String colUserMobile, String colUserCompany, String colUserIndustry, String colUserJob,
            String colUserEmail, String colUserAddress, Integer colReceiveMsgFlag, Integer colFriendInvite,
            Integer colShowDetailInner, Integer colShowDetailOutter, Integer updateBy);

    /**
     * 查询本机构成员
     * 
     * @param pageCondition
     * @param orgId
     * @param colUserMobile
     * @return
     */
    public Page<OrgMember> queryMyOrgMembers(PageCondition pageCondition, Integer orgId, String colUserMobile);

    /**
     * 创建本机构用户
     * 
     * @param orgId
     * @param positionId
     * @param colUserName
     * @param colUserMobile
     * @param colUserCompany
     * @param colUserEmail
     * @param colUserAddress
     * @param colReceiveMsgFlag
     * @param colShowDetailInner
     * @param colShowDetailOutter
     * @param createBy
     * @return
     * @throws ICPIMException
     */
    public int createMyOrgMember(Integer orgId, Integer positionId, String colUserName, String colUserMobile,
            String colUserCompany, String colUserIndustry, String colUserJob, String colUserEmail,
            String colUserAddress, Integer colReceiveMsgFlag, Integer colFriendInvite, Integer colShowDetailInner,
            Integer colShowDetailOutter, Integer createBy) throws ICPIMException;

    /**
     * 修改本机构成员
     * 
     * @param orgMemInternalId
     * @param userId
     * @param orgId
     * @param positionId
     * @param colUserName
     * @param colUserMobile
     * @param colUserCompany
     * @param colUserEmail
     * @param colUserAddress
     * @param colReceiveMsgFlag
     * @param colShowDetailInner
     * @param colShowDetailOutter
     * @param updateBy
     * @return
     */
    public int updateMyOrgMember(Integer orgMemInternalId, Integer userId, Integer orgId, Integer positionId,
            String colUserName, String colUserMobile, String colUserCompany, String colUserIndustry, String colUserJob,
            String colUserEmail, String colUserAddress, Integer colReceiveMsgFlag, Integer colFriendInvite,
            Integer colShowDetailInner, Integer colShowDetailOutter, Integer updateBy);

    /**
     * 添加机构成员（关联已有appUser）
     * 
     * @return
     * @throws ICPIMException
     */
    public int addOrgMember(Integer userId, Integer[] orgIdArr, Integer[] positionIdArr, Integer colShowDetailInner,
            Integer colShowDetailOutter, Integer createBy) throws ICPIMException;

    // /**
    // * 添加机构成员（关联已有appUser）
    // *
    // * @return
    // */
    // public int addMyOrgMember(Integer userId, Integer orgId,
    // Integer positionId, Integer colShowDetailInner,
    // Integer colShowDetailOutter, Integer createBy);

}
