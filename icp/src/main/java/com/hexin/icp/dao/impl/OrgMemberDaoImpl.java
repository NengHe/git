package com.hexin.icp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.hexin.core.dao.BaseDaoSupport;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.Group;
import com.hexin.icp.bean.OrgMember;
import com.hexin.icp.dao.OrgMemberDao;

@Repository
public class OrgMemberDaoImpl extends BaseDaoSupport implements OrgMemberDao {

    private final String INSERT_ORG_GROUP_MEMBER = "INSERT INTO t_org_group_mem(col_org_group_id,col_org_group_user_id) VALUES(?,?)";
    private final String GET_GROUP_BY_ORG_ID     = "select col_org_group_id as groupId,col_org_group_name as groupName from t_org_group where col_org_group_org_id=?";

    @Override
    public List<OrgMember> queryOrgMembers4Sys(String colUserMobile) {

        String sql = "SELECT om.org_mem_internal_id,om.col_show_detail_inner,om.col_show_detail_outter,om.col_org_user_status,o.org_id,o.col_org_code,o.col_org_name,p.position_id,p.col_pos_name,";
        sql += " au.user_id,au.col_user_name,au.col_user_company,au.col_user_job,au.col_user_email,au.col_user_mobile,au.col_user_address,au.col_receive_msg_flag,au.col_friend_invite,au.col_user_industry";
        sql += " FROM t_org_members om";
        sql += " inner JOIN t_org o ON o.org_id=om.col_org_id";
        sql += " inner JOIN t_app_user au ON au.user_id=om.col_org_user_id";
        sql += " LEFT JOIN t_position p ON p.position_id=om.col_org_user_position_id";
        sql += " where om.col_org_user_status='0'";

        if (StringUtils.isNotEmpty(colUserMobile)) {
            sql += " and au.col_user_mobile like '" + colUserMobile + "%'";
        }

        List<OrgMember> list = super.findList(sql, OrgMember.class);

        return list;
    }

    @Override
    public List<OrgMember> queryOrgMembers4Org(Integer orgId, String colUserMobile) {

        String sql = "SELECT om.org_mem_internal_id,om.col_show_detail_inner,om.col_show_detail_outter,om.col_org_user_status,o.org_id,o.col_org_code,o.col_org_name,p.position_id,p.col_pos_name,";
        sql += " au.user_id,au.col_user_name,au.col_user_company,au.col_user_job,au.col_user_email,au.col_user_mobile,au.col_user_address,au.col_receive_msg_flag,au.col_friend_invite,au.col_user_industry";
        sql += " FROM t_org_members om";
        sql += " inner JOIN t_org o ON o.org_id=om.col_org_id";
        sql += " inner JOIN t_app_user au ON au.user_id=om.col_org_user_id";
        sql += " LEFT JOIN t_position p ON p.position_id=om.col_org_user_position_id";
        sql += " where om.col_org_user_status='0' and om.col_org_id=?";

        if (StringUtils.isNotEmpty(colUserMobile)) {
            sql += " and au.col_user_mobile like '" + colUserMobile + "%'";
        }

        List<OrgMember> list = super.findList(sql, OrgMember.class, orgId);

        return list;
    }

    @Override
    public Integer insertAppUser(String colUserName, String colUserMobile, String colUserCompany,
            String colUserIndustry, String colUserJob, String colUserEmail, String colUserAddress,
            Integer colReceiveMsgFlag, Integer colFriendInvite, Integer createBy) {

        String sql = "insert into t_app_user(col_user_login_id,col_user_name,col_user_mobile,col_user_email,col_user_address,col_user_company,col_user_industry,col_user_job,col_receive_msg_flag,col_friend_invite,create_by,create_time) "
                + "values(:col_user_login_id,:col_user_name,:col_user_mobile,:col_user_email,:col_user_address,:col_user_company,:col_user_industry,:col_user_job,:col_receive_msg_flag,:col_friend_invite,:create_by,now())";

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("col_user_login_id", colUserMobile); // 手机号作为登陆id
        args.put("col_user_name", colUserName);
        args.put("col_user_mobile", colUserMobile);
        args.put("col_user_company", colUserCompany);
        args.put("col_user_industry", colUserIndustry);
        args.put("col_user_job", colUserJob);
        args.put("col_user_email", colUserEmail);
        args.put("col_user_address", colUserAddress);
        args.put("col_receive_msg_flag", colReceiveMsgFlag);
        args.put("col_friend_invite", colFriendInvite);
        args.put("create_by", createBy);

        Integer appUserID = super.insertReturnPK(sql, args);

        return appUserID;
    }

    @Override
    public void batchInsetOrgMembers(List<Object[]> params) {

        String sql = "insert into t_org_members(col_org_id,col_org_user_id,col_org_user_position_id,col_org_user_join_date,col_show_detail_inner,col_show_detail_outter) "
                + "values(?,?,?,now(),?,?)";

        super.batchUpdate(sql, params);
    }

    @Override
    public int updateAppUser(Integer userId, String colUserName, String colUserMobile, String colUserCompany,
            String colUserIndustry, String colUserJob, String colUserEmail, String colUserAddress,
            Integer colReceiveMsgFlag, Integer colFriendInvite, Integer updateBy) {

        String sql = "update t_app_user set col_user_mobile=?,col_user_name=?,col_user_mobile=?,col_user_company=?,col_user_industry=?,col_user_job=?,col_user_email=?,col_user_address=?,col_receive_msg_flag=?,col_friend_invite=?,last_update_by=?,last_update_time=now() where user_id=?";

        int affectedRows = super.update(sql, colUserMobile, colUserName, colUserMobile, colUserCompany,
                colUserIndustry, colUserJob, colUserEmail, colUserAddress, colReceiveMsgFlag, colFriendInvite,
                updateBy, userId);

        return affectedRows;
    }

    // @Override
    // public int deleteOrgMembersByUserId(Integer userId) {
    // String sql = "delete from t_org_members where col_org_user_id=?";
    //
    // int affectedRows = super.update(sql, userId);
    //
    // return affectedRows;
    // }

    @Override
    public int insetOrgMembers(Integer orgId, Integer appUserId, Integer positionId, Integer colShowDetailInner,
            Integer colShowDetailOutter) {

        int affectedRows = 0;
        String sql = null;

        sql = "insert into t_org_members(col_org_id,col_org_user_id,col_org_user_position_id,col_org_user_join_date,col_show_detail_inner,col_show_detail_outter) "
                + "values(?,?,?,now(),?,?)";

        affectedRows = super.insert(sql, orgId, appUserId, positionId, colShowDetailInner, colShowDetailOutter);

        return affectedRows;
    }

    @Override
    public int updateOrgMembers(Integer orgMemInternalId, Integer positionId, Integer colShowDetailInner,
            Integer colShowDetailOutter) {

        int affectedRows = 0;
        String sql = null;

        sql = "update t_org_members set col_org_user_position_id=?,col_show_detail_inner=?,col_show_detail_outter=? where org_mem_internal_id=? ";

        affectedRows = super.update(sql, positionId, colShowDetailInner, colShowDetailOutter, orgMemInternalId);

        return affectedRows;
    }

    @Override
    public OrgMember getOrgMembersByOrgIdAndUserId(Integer reqOrgId, Integer appUserId) {

        OrgMember result = null;
        String sql = null;

        sql = "select * from t_org_members where col_org_user_status='0' and col_org_id=? and col_org_user_id=? ";

        result = super.findUnique(sql, OrgMember.class, reqOrgId, appUserId);

        return result;
    }

    @Override
    public void batchUpdateOrgMembers(List<Object[]> batchUpdateParams) {

        String sql = "update t_org_members set col_org_user_position_id=?,col_show_detail_inner=?,col_show_detail_outter=? where col_org_id=? and col_org_user_id=? ";

        super.batchUpdate(sql, batchUpdateParams);
    }

    @Override
    public void batchSoftDeleteOrgMembersByOrgIdAndUserId(List<Object[]> batchDeleteParams) {

        String sql;

        sql = "update t_org_members set col_org_user_status='1' where col_org_id=? and col_org_user_id=?";

        super.batchUpdate(sql, batchDeleteParams);

    }

    @Override
    public List<OrgMember> queryOrgMembers(Integer userId) {

        List<OrgMember> result;
        String sql;

        sql = "SELECT col_org_id as orgId,col_org_user_id as userId FROM t_org_members where col_org_user_status='0'";

        if (userId != null) {
            sql += " and col_org_user_id=" + userId;
        }

        result = super.findList(sql, OrgMember.class);

        return result;
    }

    @Override
    public Group getGroupIdByOrgId(Integer orgId) {

        Group result = null;

        result = super.findUnique(GET_GROUP_BY_ORG_ID, Group.class, orgId);

        return result;
    }

    @Override
    public void batchInsertOrgGroupMembers(List<Object[]> groupMemberParams) {

        super.batchUpdate(INSERT_ORG_GROUP_MEMBER, groupMemberParams);

    }

    @Override
    public int insertOrgGroupMember(Integer groupId, Integer appUserId) {

        int affectedRows = 0;

        affectedRows = super.insert(INSERT_ORG_GROUP_MEMBER, groupId, appUserId);

        return affectedRows;
    }

    @Override
    public Page<OrgMember> queryOrgMembersPage4Org(PageCondition pageCondition, Integer orgId, String colUserMobile) {

        Page<OrgMember> page = null;

        String sql = "SELECT om.org_mem_internal_id,om.col_show_detail_inner,om.col_show_detail_outter,om.col_org_user_status,o.org_id,o.col_org_code,o.col_org_name,p.position_id,p.col_pos_name,";
        sql += " au.user_id,au.col_user_name,au.col_user_company,au.col_user_job,au.col_user_email,au.col_user_mobile,au.col_user_address,au.col_receive_msg_flag,au.col_friend_invite,au.col_user_industry";
        sql += " FROM t_org_members om";
        sql += " inner JOIN t_org o ON o.org_id=om.col_org_id";
        sql += " inner JOIN t_app_user au ON au.user_id=om.col_org_user_id";
        sql += " LEFT JOIN t_position p ON p.position_id=om.col_org_user_position_id";
        sql += " where om.col_org_user_status='0' and om.col_org_id=?";

        if (StringUtils.isNotEmpty(colUserMobile)) {
            sql += " and au.col_user_mobile like '" + colUserMobile + "%'";
        }

        page = super.findPage(sql, OrgMember.class, pageCondition, orgId);

        return page;
    }

}
