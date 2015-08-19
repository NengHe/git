package com.hexin.icp.dao.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.hexin.core.dao.BaseDaoSupport;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.ActParticipants;
import com.hexin.icp.bean.Activity;
import com.hexin.icp.bean.CommentReply;
import com.hexin.icp.bean.OrgReq;
import com.hexin.icp.bean.PaymentHistoryDTO;
import com.hexin.icp.dao.ActivityDao;

@Repository
public class ActivityDaoImpl extends BaseDaoSupport implements ActivityDao {

    private final String GET_ACTIVITY_BY_ACTIVITY_ID             = "select col_activity_cost from t_activity where activity_id=?";
    private final String GET_PAYMENT_HISTORY_BY_USER_ACTIVITY_ID = "select col_get_account as colReceiverAccount,col_money from t_pay_history where col_user_act_id=? and col_app_user_id=? and col_pay_type=?";
    private final String INSERT_PAYMENT_HISTORY                  = "INSERT INTO  t_pay_history(col_user_act_id,col_app_user_id,col_payment_type,col_pay_account,col_get_account,col_money,col_time,col_pay_type) "
                                                                         + "VALUES(?,?,?,?,?,?,now(),?)";
    private final String UPDATE_USER_ACT_PAYMENT_STATUS          = "update t_user_act set col_payment_status=? where col_user_activity_id=?";

    @Override
    public Page<Activity> findOrgActsByOrgId(PageCondition pageCondition, Integer orgId, String colActivityTitle)
            throws SecurityException, IllegalArgumentException, SQLException, InstantiationException,
            IllegalAccessException, NoSuchFieldException, IOException {

        String sql = "SELECT  a.*, o.col_org_code, o.col_org_name, al.col_label_text AS colActivityIndexLabel";
        sql += " FROM t_activity a";
        sql += " LEFT JOIN t_app_lable_index al ON al.dict_id = a.col_activity_index_id";
        sql += " LEFT JOIN t_org o ON o.org_id = a.col_activity_issue_org_id";
        sql += " WHERE a.col_del_flag=0 AND a.col_activity_issue_org_type=2 and a.col_activity_issue_org_id=?";

        if (StringUtils.isNotEmpty(colActivityTitle)) {
            sql += " and a.col_activity_title like '%" + colActivityTitle + "%'";
        }

        Page<Activity> result = super.findPageWithBlob(sql, Activity.class, pageCondition, orgId);

        return result;
    }

    @Override
    public Page<Activity> findAllActs(PageCondition pageCondition, String colActivityTitle) throws SecurityException,
            IllegalArgumentException, SQLException, InstantiationException, IllegalAccessException,
            NoSuchFieldException, IOException {

        String sql = "SELECT  a.*, o.col_org_code, o.col_org_name,  al.col_label_text AS colActivityIndexLabel";
        sql += " FROM t_activity a";
        sql += " LEFT JOIN t_app_lable_index al ON al.dict_id = a.col_activity_index_id";
        sql += " LEFT JOIN t_org o ON o.org_id = a.col_activity_issue_org_id";
        sql += " WHERE a.col_del_flag=0  ";

        if (StringUtils.isNotEmpty(colActivityTitle)) {
            sql += " and a.col_activity_title like '%" + colActivityTitle + "%'";
        }

        Page<Activity> result = super.findPageWithBlob(sql, Activity.class, pageCondition);

        return result;
    }

    @Override
    public Integer createActivity(String colActivityIssueOrgType, Integer orgId, String colActivityTitle,
            String colActivityTitleImgUrl, String colActivityBrief, String colActivityBriefImgUrl,
            Integer colActivityAuditableFlag, Integer colActivityNeedPayFlag, String colActivityEnrollDeadline,
            String colActivityStartTime, String colActivityEndTime, String colActivityType,
            String colActivityBackground, String colActivityAgenda, String colActivityGuest,
            String colActivityOrgnizer, String colActivityPlace, Float colActivityPlaceLatitude,
            Float colActivityPlaceLongitude, String colActivityContent, String cntDetailUrl,
            Integer colActivityIndexId, Float colActivityCost, Integer colActivitySharableFlag, Integer colCreateBy) {

        String sql = "insert into t_activity(col_activity_type,col_activity_index_id,col_activity_orgnizer,col_activity_title,col_activity_title_img_url,"
                + "col_activity_brief,col_activity_brief_img_url,col_activity_start_time,col_activity_end_time,col_activity_place,"
                + "col_activity_place_latitude,col_activity_place_longitude,col_activity_background,col_activity_agenda,"
                + "col_activity_guest,col_activity_content,col_activity_content_detail_url,col_activity_issue_org_id,col_activity_issue_org_type,"
                + "col_activity_cost,col_activity_sharable_flag,create_time,create_by,col_activity_auditable_flag,col_activity_need_pay_flag,col_activity_enroll_deadline) "
                + " values(:col_activity_type,:col_activity_index_id,:col_activity_orgnizer,:col_activity_title,:col_activity_title_img_url,"
                + ":col_activity_brief,:col_activity_brief_img_url,:col_activity_start_time,:col_activity_end_time,:col_activity_place,"
                + ":col_activity_place_latitude,:col_activity_place_longitude,:col_activity_background,:col_activity_agenda,"
                + ":col_activity_guest,:col_activity_content,:col_activity_content_detail_url,:col_activity_issue_org_id,:col_activity_issue_org_type,"
                + ":col_activity_cost,:col_activity_sharable_flag,now(),:create_by,:col_activity_auditable_flag,:col_activity_need_pay_flag,:col_activity_enroll_deadline)";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("col_activity_type", colActivityType);
        params.put("col_activity_index_id", colActivityIndexId);
        params.put("col_activity_orgnizer", colActivityOrgnizer);
        params.put("col_activity_title", colActivityTitle);
        params.put("col_activity_title_img_url", colActivityTitleImgUrl);
        params.put("col_activity_brief", colActivityBrief);
        params.put("col_activity_brief_img_url", colActivityTitleImgUrl);
        params.put("col_activity_start_time", colActivityStartTime);
        params.put("col_activity_end_time", colActivityEndTime);
        params.put("col_activity_place", colActivityPlace);
        params.put("col_activity_place_latitude", colActivityPlaceLatitude);
        params.put("col_activity_place_longitude", colActivityPlaceLongitude);
        params.put("col_activity_background", colActivityBackground);
        params.put("col_activity_agenda", colActivityAgenda);
        params.put("col_activity_guest", colActivityGuest);
        params.put("col_activity_content", colActivityContent);
        params.put("col_activity_content_detail_url", cntDetailUrl);
        params.put("col_activity_issue_org_id", orgId);
        params.put("col_activity_issue_org_type", colActivityIssueOrgType);
        params.put("col_activity_cost", colActivityCost);
        params.put("col_activity_sharable_flag", colActivitySharableFlag);
        params.put("create_by", colCreateBy);
        params.put("col_activity_auditable_flag", colActivityAuditableFlag);
        params.put("col_activity_need_pay_flag", colActivityNeedPayFlag);
        params.put("col_activity_enroll_deadline", colActivityEnrollDeadline);

        Integer actId = super.insertReturnPK(sql, params);

        return actId;
    }

    @Override
    public int updateActivity(Integer activityId, String colActivityTitle, String colActivityBrief,
            String colActivityStartTime, String colActivityEndTime, String colActivityType,
            Integer colActivityIssueOrgId, String colActivityBackground, String colActivityAgenda,
            String colActivityGuest, String colActivityOrgnizer, String colActivityPlace,
            Float colActivityPlaceLatitude, Float colActivityPlaceLongitude, String colActivityContent,
            String cntDetailUrl, Integer colActivityIndexId, Float colActivityCost, Integer colActivitySharableFlag,
            Integer colUpdateBy, String colActivityTitleImgUrl, String colActivityBriefImgUrl,
            Integer colActivityAuditableFlag, Integer colActivityNeedPayFlag, String colActivityEnrollDeadline) {

        String sql = "update t_activity set col_activity_type=?,col_activity_issue_org_id=?,col_activity_index_id=?,col_activity_orgnizer=?,col_activity_title=?,"
                + "col_activity_brief=?,col_activity_start_time=?,col_activity_end_time=?,col_activity_place=?,"
                + "col_activity_place_latitude=?,col_activity_place_longitude=?,col_activity_background=?,col_activity_agenda=?,"
                + "col_activity_guest=?,col_activity_content=?,col_activity_content_detail_url=?,"
                + "col_activity_cost=?,col_activity_sharable_flag=?,last_update_time=now(),last_update_by=?,"
                + "col_activity_title_img_url=?,col_activity_brief_img_url=?,"
                + "col_activity_auditable_flag=?,col_activity_need_pay_flag=?,col_activity_enroll_deadline=? where activity_id=?";

        int affectedRows = super.update(sql, colActivityType, colActivityIssueOrgId, colActivityIndexId,
                colActivityOrgnizer, colActivityTitle, colActivityBrief, colActivityStartTime, colActivityEndTime,
                colActivityPlace, colActivityPlaceLatitude, colActivityPlaceLongitude, colActivityBackground,
                colActivityAgenda, colActivityGuest, colActivityContent, cntDetailUrl, colActivityCost,
                colActivitySharableFlag, colUpdateBy, colActivityTitleImgUrl, colActivityBriefImgUrl,
                colActivityAuditableFlag, colActivityNeedPayFlag, colActivityEnrollDeadline, activityId);

        return affectedRows;

    }

    @Override
    public int updateActivityDelFlag(Integer updateBy, Integer activityId) {

        String sql = "update t_activity set col_del_flag=1,last_update_by=?,last_update_time=now() where activity_id=?";

        int affectedRows = super.delete(sql, updateBy, activityId);

        return affectedRows;
    }

    @Override
    public List<ActParticipants> queryActParticipants(Integer activityId, Integer appUSerId, String colAuthStatus) {

        List<ActParticipants> result = null;
        String sql = null;

        // sql =
        // "SELECT ua.*,o.col_org_code,o.col_org_name,au.col_user_name,au.col_user_company,au.col_user_email,au.col_user_mobile,au.col_user_address ";
        sql = "SELECT ua.*,au.col_user_name,au.col_user_company,au.col_user_email,au.col_user_mobile,au.col_user_address ";
        sql += " FROM t_user_act ua";
        sql += " LEFT JOIN t_app_user au ON au.user_id=ua.col_user_id";
        // sql +=
        // " LEFT JOIN t_org_members om ON om.col_org_user_id=au.user_id";
        // sql += " LEFT JOIN t_org o ON o.org_id=om.col_org_id";
        sql += " where ua.col_activity_id=?";

        if (appUSerId != null) {
            sql += " and ua.col_user_id=" + appUSerId;
        }
        if (StringUtils.isNotEmpty(colAuthStatus)) {
            sql += " and ua.col_auth_status='" + colAuthStatus + "'";
        }

        result = super.findList(sql, ActParticipants.class, activityId);

        return result;
    }

    @Override
    public ActParticipants getActPartcipantsById(Integer colUserActivityId) {

        ActParticipants result = null;
        String sql = null;

        sql = "SELECT * FROM t_user_act where col_user_activity_id=?";

        result = super.findUnique(sql, ActParticipants.class, colUserActivityId);

        return result;
    }

    @Override
    public int updateActParticipantsAuthStatus(Integer colUserActivityId, String colAuthStatus) {

        int affectedRows;
        String sql = null;

        sql = "update t_user_act set col_auth_status=? where col_user_activity_id=?";

        affectedRows = super.update(sql, colAuthStatus, colUserActivityId);

        return affectedRows;
    }

    @Override
    public int deleteActParticipants(Integer colUserActivityId) {

        int affectedRows;
        String sql = null;

        sql = "delete from t_user_act where col_user_activity_id=?";

        affectedRows = super.update(sql, colUserActivityId);

        return affectedRows;
    }

    @Override
    public List<CommentReply> queryActComments(Integer activityId) {

        List<CommentReply> result = null;
        String sql;

        sql = "SELECT ac.*,cr.*,cau.col_user_name as colUserName,rau.col_admin_username as colCurReplyUserName";
        sql += "  FROM t_act_comment ac";
        sql += "  LEFT JOIN t_app_user cau ON cau.user_id=ac.col_user_id";
        sql += "  LEFT JOIN t_comment_reply cr ON cr.col_comment_id=ac.col_act_comment_id";
        sql += "  LEFT JOIN t_admin_user rau ON rau.admin_user_id=cr.col_cur_reply_user_id";
        sql += "  where ac.col_activity_id=?";
        sql += "  order by col_comment_create_time";

        result = super.findList(sql, CommentReply.class, activityId);

        return result;
    }

    @Override
    public int insertActCommentReply(Integer colActCommentId, String colReplyContent, Integer replyUserType,
            Integer colCurReplyUserId, String colCurReplyUserName) {

        int affectedRows;
        String sql = null;

        sql = "insert into t_comment_reply(col_comment_id,col_reply_content,col_reply_user_type,col_cur_reply_user_id,col_cur_reply_user_name,col_reply_create_time) "
                + "values(?,?,?,?,?,now())";

        affectedRows = super.update(sql, colActCommentId, colReplyContent, replyUserType, colCurReplyUserId,
                colCurReplyUserName);

        return affectedRows;
    }

    @Override
    public int updateActCommentShowStatus(Integer colActCommentId, Integer colCommentShowStatus) {

        int affectedRows;
        String sql = null;

        sql = "update t_act_comment set col_comment_show_status=? where col_act_comment_id=?";

        affectedRows = super.update(sql, colCommentShowStatus, colActCommentId);

        return affectedRows;
    }

    @Override
    public int updateActCommentReply(Integer replyId, String colReplyContent) {

        int affectedRows;
        String sql = null;

        sql = "update t_comment_reply set col_reply_content=? where reply_id=?";

        affectedRows = super.update(sql, colReplyContent, replyId);

        return affectedRows;
    }

    @Override
    public int updateUserActPayStatus(Integer colUserActivityId, Integer colPaymentStatus) {
        int affectedRows = 0;

        affectedRows = super.update(UPDATE_USER_ACT_PAYMENT_STATUS, colPaymentStatus, colUserActivityId);

        return affectedRows;
    }

    @Override
    public int insertPayHistory(Integer colUserActivityId, Integer colUserId, Integer colPaymentType,
            String colPayAccount, String colReceiverAccount, Double colMoney, Integer colPayType) {
        int affectedRows = 0;

        affectedRows = super.update(INSERT_PAYMENT_HISTORY, colUserActivityId, colUserId, colPaymentType,
                colPayAccount, colReceiverAccount, colMoney, colPayType);

        return affectedRows;
    }

    @Override
    public PaymentHistoryDTO getPaymentHistoryByUserActivityId(Integer colUserActivityId, Integer colAppUserId,
            Integer colPayType) {
        PaymentHistoryDTO result = null;

        result = super.findUnique(GET_PAYMENT_HISTORY_BY_USER_ACTIVITY_ID, PaymentHistoryDTO.class, colUserActivityId,
                colAppUserId, colPayType);

        return result;
    }

    @Override
    public Activity getCostByActivityId(String colActivityId) {
        Activity result = null;

        result = super.findUnique(GET_ACTIVITY_BY_ACTIVITY_ID, Activity.class, colActivityId);

        return result;
    }
}
