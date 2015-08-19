package com.hexin.icp.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.ActParticipants;
import com.hexin.icp.bean.Activity;
import com.hexin.icp.bean.CommentReply;
import com.hexin.icp.bean.PaymentHistoryDTO;

public interface ActivityDao {

    /**
     * 查询某机构发布的活动列表
     * 
     * @param pageCondition
     * @param orgId
     * @param colActivityTitle
     * @return
     * @throws IOException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws SecurityException
     */
    public Page<Activity> findOrgActsByOrgId(PageCondition pageCondition, Integer orgId, String colActivityTitle)
            throws SecurityException, IllegalArgumentException, SQLException, InstantiationException,
            IllegalAccessException, NoSuchFieldException, IOException;

    /**
     * 查询所有机构发布的活动列表
     * 
     * @param pageCondition
     * @param colActivityTitle
     * @return
     * @throws IOException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws SecurityException
     */
    public Page<Activity> findAllActs(PageCondition pageCondition, String colActivityTitle) throws SecurityException,
            IllegalArgumentException, SQLException, InstantiationException, IllegalAccessException,
            NoSuchFieldException, IOException;

    /**
     * 创建活动
     * 
     * @param colActivityIssueOrgType
     * @param orgId
     * @param colActivityTitle
     * @param colActivityBrief
     * @param colActivityStartTime
     * @param colActivityEndTime
     * @param colActivityOrgnizer
     * @param colActivityPlace
     * @param colActivityContent
     * @param colActivityIndexId
     * @param colActivityCost
     * @param colActivitySharableFlag
     * @param colCreateBy
     * @return
     */
    public Integer createActivity(String colActivityIssueOrgType, Integer orgId, String colActivityTitle,
            String colActivityTitleImgUrl, String colActivityBrief, String colActivityBriefImgUrl,
            Integer colActivityAuditableFlag, Integer colActivityNeedPayFlag, String colActivityEnrollDeadline,
            String colActivityStartTime, String colActivityEndTime, String colActivityType,
            String colActivityBackground, String colActivityAgenda, String colActivityGuest,
            String colActivityOrgnizer, String colActivityPlace, Float colActivityPlaceLatitude,
            Float colActivityPlaceLongitude, String colActivityContent, String cntDetailUrl,
            Integer colActivityIndexId, Float colActivityCost, Integer colActivitySharableFlag, Integer colCreateBy);

    /**
     * 更新活动
     * 
     * @param activityId
     * @param colActivityTitle
     * @param colActivityBrief
     * @param colActivityStartTime
     * @param colActivityEndTime
     * @param colActivityType
     * @param colActivityBackground
     * @param colActivityAgenda
     * @param colActivityGuest
     * @param colActivityOrgnizer
     * @param colActivityPlace
     * @param colActivityPlaceLatitude
     * @param colActivityPlaceLongitude
     * @param colActivityContent
     * @param cntDetailUrl
     * @param colActivityIndexId
     * @param colActivityCost
     * @param colActivitySharableFlag
     * @param colUpdateBy
     * @return
     */
    public int updateActivity(Integer activityId, String colActivityTitle, String colActivityBrief,
            String colActivityStartTime, String colActivityEndTime, String colActivityType,
            Integer colActivityIssueOrgId, String colActivityBackground, String colActivityAgenda,
            String colActivityGuest, String colActivityOrgnizer, String colActivityPlace,
            Float colActivityPlaceLatitude, Float colActivityPlaceLongitude, String colActivityContent,
            String cntDetailUrl, Integer colActivityIndexId, Float colActivityCost, Integer colActivitySharableFlag,
            Integer colUpdateBy, String colActivityTitleImgUrl, String colActivityBriefImgUrl,
            Integer colActivityAuditableFlag, Integer colActivityNeedPayFlag, String colActivityEnrollDeadline);

    /**
     * 软删除活动
     * 
     * @param adminUserId
     * @param activityId
     * @return
     */
    public int updateActivityDelFlag(Integer updateBy, Integer activityId);

    /**
     * 查询活动参与列表
     * 
     * @param activityId
     * @return
     */
    public List<ActParticipants> queryActParticipants(Integer activityId, Integer appUSerId, String colAuthStatus);

    /**
     * 根据id查询活动参与者
     * 
     * @param colUserActivityId
     * @return
     */
    public ActParticipants getActPartcipantsById(Integer colUserActivityId);

    /**
     * 修改t_user_act审核状态
     * 
     * @param colUserActivityId
     * @return
     */
    public int updateActParticipantsAuthStatus(Integer colUserActivityId, String colAuthStatus);

    /**
     * 删除活动参与者
     * 
     * @param colUserActivityId
     * @return
     */
    public int deleteActParticipants(Integer colUserActivityId);

    /**
     * 查询评论回复列表
     * 
     * @param activityId
     * @return
     */
    public List<CommentReply> queryActComments(Integer activityId);

    /**
     * 插入活动评论回复记录
     * 
     * @param colActCommentId
     * @param colReplyContent
     * @param replyUserType
     * @param colCurReplyUserId
     * @param colCurReplyUserName
     * @return
     */
    public int insertActCommentReply(Integer colActCommentId, String colReplyContent, Integer replyUserType,
            Integer colCurReplyUserId, String colCurReplyUserName);

    /**
     * 更新评论显示状态
     * 
     * @return
     */
    public int updateActCommentShowStatus(Integer colActCommentId, Integer colCommentShowStatus);

    /**
     * 更新评论回复
     * 
     * @param replyId
     * @param colReplyContent
     * @return
     */
    public int updateActCommentReply(Integer replyId, String colReplyContent);

    /**
     * 退款（更新t_user_act的col_payment_status=2）
     * 
     * @param colUserActivityId
     * @param colPaymentStatus
     * @return
     */
    public int updateUserActPayStatus(Integer colUserActivityId, Integer colPaymentStatus);

    /**
     * 生成支付流水记录
     * 
     * @param colUserActivityId
     * @param colUserId
     * @param colPaymentType
     * @param colPayAccount
     * @param colReceiverAccount
     * @param colMoney
     * @param colPayType
     * @return
     */
    public int insertPayHistory(Integer colUserActivityId, Integer colUserId, Integer colPaymentType,
            String colPayAccount, String colReceiverAccount, Double colMoney, Integer colPayType);

    /**
     * 根据参与id查询支付流水记录
     * 
     * @param colUserActivityId
     * @param colPayType
     * @return
     */
    public PaymentHistoryDTO getPaymentHistoryByUserActivityId(Integer colUserActivityId, Integer colAppUserId,
            Integer colPayType);

    public Activity getCostByActivityId(String colActivityId);

}
