package com.hexin.icp.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.hexin.core.dao.BaseDaoSupport;
import com.hexin.icp.bean.AppUser;
import com.hexin.icp.bean.Dict;
import com.hexin.icp.bean.Index;
import com.hexin.icp.bean.Org;
import com.hexin.icp.bean.OrgMember;
import com.hexin.icp.bean.PaymentTypeDTO;
import com.hexin.icp.bean.Position;
import com.hexin.icp.bean.ZTreeDTO;
import com.hexin.icp.dao.CommonDao;

@Repository
public class CommonDaoImpl extends BaseDaoSupport implements CommonDao {

    private final String QUERY_PAYMENT_BY_PAYMENTTYPE_FOR_SELECT = "select pay_id,col_pay_account from t_payment where col_payment_type=?";
    private final String GET_DICT_MAX_VAL = "select max(col_dict_val) from t_dict where col_dict_class=?";

    @Override
    public List<Position> findPositions4select(Integer orgId) {
        String sql = "select position_id,col_pos_name from t_position where col_pos_org_id=?";

        List<Position> list = super.findList(sql, Position.class, orgId);

        return list;
    }

    @Override
    public Org getOrgByOrgId4select(Integer orgId) {
        String sql = "select org_id,col_org_name from t_org where org_id=?";

        Org org = super.findUnique(sql, Org.class, orgId);

        return org;
    }

    @Override
    public List<Org> findAllOrgs4select() {
        String sql = "select org_id,col_org_name from t_org";

        List<Org> list = super.findList(sql, Org.class);

        return list;
    }

    @Override
    public List<Index> queryIndex4select(String colLabelClass) {
        String sql = "select dict_id,col_label_text from t_app_lable_index where 1=1";

        if (StringUtils.isNotEmpty(colLabelClass)) {
            sql += " and col_label_class='" + colLabelClass + "'";
        }

        List<Index> list = super.findList(sql, Index.class);

        return list;
    }

    @Override
    public List<Index> queryIndex(String colLabelClass) {

        String sql = "select * from t_app_lable_index where col_label_class=?";

        List<Index> list = super.findList(sql, Index.class, colLabelClass);

        return list;
    }

    @Override
    public int createIndex(String colLabelClass, String colLabelText, String colDelFlag) {
        int affectedRows;
        String sql;

        sql = "insert into t_app_lable_index(col_label_class,col_label_text,col_create_time,col_del_flag) values(?,?,now(),?)";

        affectedRows = super.insert(sql, colLabelClass, colLabelText, colDelFlag);

        return affectedRows;
    }

    @Override
    public int removeIndex(Integer dictId) {
        int affectedRows;
        String sql;

        sql = "delete from t_app_lable_index where dict_id=?";

        affectedRows = super.delete(sql, dictId);

        return affectedRows;
    }

    @Override
    public int udpateIndex(Integer dictId, String colLabelClass, String colLabelText, String colDelFlag) {
        int affectedRows;
        String sql;

        sql = "update t_app_lable_index set col_label_text=?,col_del_flag=? where dict_id=? and col_label_class=?";

        affectedRows = super.insert(sql, colLabelText, colDelFlag, dictId, colLabelClass);

        return affectedRows;
    }

    @Override
    public List<ZTreeDTO> getRoleTree4select(String roleType) {
        List<ZTreeDTO> result;
        String sql;

        sql = "SELECT r.role_id AS id,ifnull(r.parent_id,0) AS pId,r.role_name AS name,r.role_type as type FROM t_role r where r.del_flag='0' ";

        if (StringUtils.isNotEmpty(roleType)) {
            sql += " and r.role_type='" + roleType + "'";
        }

        result = super.findList(sql, ZTreeDTO.class);

        return result;
    }

    @Override
    public List<Org> queryOrgs(String orgCode) {
        List<Org> result = null;
        String sql = null;

        sql = "select * from t_org where 1=1";

        if (StringUtils.isNotEmpty(orgCode)) {
            sql += " and col_org_code ='" + orgCode + "'";
        }

        result = super.findList(sql, Org.class);

        return result;
    }

    @Override
    public List<Dict> queryDict4select(String colDictClass, String colDictVal) {
        List<Dict> result = null;

        String sql = "select * from t_dict where 1=1";

        if (StringUtils.isNotEmpty(colDictClass)) {
            sql += " and col_dict_class='" + colDictClass + "'";
        }
        if (StringUtils.isNotEmpty(colDictVal)) {
            sql += " and col_dict_val='" + colDictVal + "'";
        }

        result = super.findList(sql, Dict.class);

        return result;
    }

    @Override
    public List<Dict> queryDicts(String colDictClass) {

        String sql = "select * from t_dict where col_dict_class=?";

        List<Dict> list = super.findList(sql, Dict.class, colDictClass);

        return list;
    }

    @Override
    public int createDict(String colDictClass, String colDictText, String colDictVal) {
        int affectedRows;
        String sql;

        sql = "insert into t_dict(col_dict_class,col_dict_key,col_dict_text,col_dict_val,col_dict_create_time) values(?,'orgType',?,?,now())";

        affectedRows = super.insert(sql, colDictClass, colDictText, colDictVal);

        return affectedRows;
    }

    @Override
    public int removeDict(Integer dictId) {
        int affectedRows;
        String sql;

        sql = "delete from t_dict where dict_id=?";

        affectedRows = super.delete(sql, dictId);

        return affectedRows;
    }

    @Override
    public int udpateDict(Integer DictId, String colDictClass, String colDictText, String colDictVal) {
        int affectedRows;
        String sql;

        sql = "update t_dict set col_dict_text=?,col_dict_val=? where dict_id=? and col_dict_class=?";

        affectedRows = super.insert(sql, colDictText, colDictVal, DictId, colDictClass);

        return affectedRows;
    }

    @Override
    public List<AppUser> queryAppUser(String phone) {
        List<AppUser> result = null;
        String sql = null;

        sql = "select * from t_app_user ";

        if (StringUtils.isNotEmpty(phone)) {
            sql += "where col_user_login_id like '%" + phone + "%'";
        }

        result = super.findList(sql, AppUser.class);

        return result;
    }

    @Override
    public List<AppUser> queryAppUsers4auto() {
        List<AppUser> result = null;
        String sql = null;

        sql = "select user_id,col_user_mobile from t_app_user where col_del_flag='0' ";

        result = super.findList(sql, AppUser.class);

        return result;
    }

    @Override
    public int createAppUser(String colUserLoginId, String colUserName, String colUserTel, String colUserMobile,
            String colUserEmail, String colUserAddress, String colUserJob, String colUserIndustry,
            String colUserCompany, Integer colReceiveMsgFlag, Integer colFriendInvite, String userLoginDeviceMac,
            Integer colDelFlag, String colUserOthers) {
        int affectedRows;
        String sql;
        sql = "insert into t_app_user (col_user_login_id,col_user_name,col_user_tel,col_user_mobile,col_user_email,col_user_address,col_user_job,col_user_industry,col_user_company,col_receive_msg_flag,col_friend_invite,user_login_device_mac,col_del_flag,col_user_others,create_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
        affectedRows = super.insert(sql, colUserLoginId, colUserName, colUserTel, colUserMobile, colUserEmail,
                colUserAddress, colUserJob, colUserIndustry, colUserCompany, colReceiveMsgFlag, colFriendInvite,
                userLoginDeviceMac, colDelFlag, colUserOthers);
        return affectedRows;
    }

    @Override
    public int updateAppUser(Integer userId, String colUserLoginId, String colUserName, String colUserTel,
            String colUserMobile, String colUserEmail, String colUserAddress, String colUserJob,
            String colUserIndustry, String colUserCompany, Integer colReceiveMsgFlag, Integer colFriendInvite,
            String userLoginDeviceMac, Integer colDelFlag, String colUserOthers) {
        int affectedRows;
        String sql;
        sql = "update t_app_user set col_user_login_id=?,col_user_name=?,col_user_tel=?,col_user_mobile=?,col_user_email=?,col_user_address=?,col_user_job=?,col_user_industry=?,col_user_company=?,col_receive_msg_flag=?,col_friend_invite=?,user_login_device_mac=?,col_del_flag=?,col_user_others=? where user_id=?";
        affectedRows = super.update(sql, colUserLoginId, colUserName, colUserTel, colUserMobile, colUserEmail,
                colUserAddress, colUserJob, colUserIndustry, colUserCompany, colReceiveMsgFlag, colFriendInvite,
                userLoginDeviceMac, colDelFlag, colUserOthers, userId);
        return affectedRows;
    }

    @Override
    public int removeAppUser(Integer userId) {
        int affectedRows;
        String sql;
        sql = "delete from t_app_user where user_id=?";
        affectedRows = super.delete(sql, userId);
        return affectedRows;
    }

    @Override
    public String getDictMaxVal(String colDictClass) {
        String result = null;

        result = super.queryForObject(GET_DICT_MAX_VAL, String.class, colDictClass);

        return result;
    }

    @Override
    public List<PaymentTypeDTO> queryPaymentByPaymentType4select(Integer colPaymentType) {
        List<PaymentTypeDTO> result = null;

        result = super.findList(QUERY_PAYMENT_BY_PAYMENTTYPE_FOR_SELECT, PaymentTypeDTO.class, colPaymentType);

        return result;
    }

}
