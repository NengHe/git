package com.hexin.icp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import org.csource.common.MyException;
import org.springframework.web.multipart.MultipartFile;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.ActParticipants;
import com.hexin.icp.bean.Activity;
import com.hexin.icp.bean.CommentReplyDTO;
import com.hexin.icp.bean.PaymentHistoryDTO;
import com.hexin.icp.bean.ReturnMessage;

public interface ActivityService {

    /**
     * 查询活动列表
     * 
     * @param pageCondition
     * @param adminUserId
     * @param adminUserType
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
    public Page<Activity> queryActivities(PageCondition pageCondition, Integer adminUserId, String adminUserType,
            Integer orgId, String colActivityTitle) throws SecurityException, IllegalArgumentException, SQLException,
            InstantiationException, IllegalAccessException, NoSuchFieldException, IOException;

    /**
     * 创建活动
     * 
     * @param adminUserId
     * @param adminUserType
     * @param orgId
     * @param colActivityTitle
     * @param colActivityBrief
     * @param activityTitleImg
     * @param colActivityStartTime
     * @param colActivityEndTime
     * @param colActivityOrgnizer
     * @param colActivityPlace
     * @param colActivityPlaceLongitude
     * @param colActivityPlaceLatitude
     * @param colActivityContent
     * @param colActivityIndexId
     * @param colActivityCost
     * @param colActivitySharableFlag
     * @return
     * @throws MyException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws FileNotFoundException
     */
    public int createActivity(Integer adminUserId, String adminUserType, Integer colActivityIssueOrgId,
            String colActivityTitle, String colActivityTitleImgUrl, String colActivityBrief,
            String colActivityBriefImgUrl, Integer colActivityAuditableFlag, Integer colActivityNeedPayFlag,
            String colActivityEnrollDeadline, String colActivityStartTime, String colActivityEndTime,
            String colActivityType, String colActivityBackground, String colActivityAgenda, String colActivityGuest,
            String colActivityOrgnizer, String colActivityPlace, Float colActivityPlaceLatitude,
            Float colActivityPlaceLongitude, String colActivityContent, Integer colActivityIndexId,
            Float colActivityCost, Integer colActivitySharableFlag) throws FileNotFoundException,
            NoSuchAlgorithmException, IOException, MyException;

    /**
     * 更新活动
     * 
     * @param adminUserId
     * @param adminUserType
     * @param orgId
     * @param activityId
     * @param colActivityTitle
     * @param colActivityBrief
     * @param activityTitleImg
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
     * @param colActivityIndexId
     * @param colActivityCost
     * @param colActivitySharableFlag
     * @return
     * @throws MyException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public int updateActivity(Integer adminUserId, String adminUserType, Integer orgId, Integer activityId,
            String colActivityTitle, String colActivityBrief, Integer actTitleImgId, MultipartFile activityTitleImg,
            String colActivityStartTime, String colActivityEndTime, String colActivityType,
            Integer colActivityIssueOrgId, String colActivityBackground, String colActivityAgenda,
            String colActivityGuest, String colActivityOrgnizer, String colActivityPlace,
            Float colActivityPlaceLatitude, Float colActivityPlaceLongitude, String colActivityContent,
            Integer colActivityIndexId, Float colActivityCost, Integer colActivitySharableFlag,
            String colActivityTitleImgUrl, String colActivityBriefImgUrl, Integer colActivityAuditableFlag,
            Integer colActivityNeedPayFlag, String colActivityEnrollDeadline) throws NoSuchAlgorithmException,
            IOException, MyException;

    /**
     * 删除活动（软删除）
     * 
     * @param adminUserId
     * @param activityId
     * @return
     */
    public int removeActivity(Integer adminUserId, Integer activityId);

    /**
     * 查看活动参与列表
     * 
     * @param activityId
     * @return
     */
    public List<ActParticipants> queryActParticipants(Integer activityId);

    /**
     * 审核活动参与通过
     * 
     * @param colUserActivityId
     * @return
     */
    public ReturnMessage auditActJoinPass(Integer colUserActivityId);

    /**
     * 审核活动参与不通过
     * 
     * @param colUserActivityId
     * @return
     */
    public ReturnMessage auditActJoinReject(Integer colUserActivityId);

    /**
     * 删除活动申请
     * 
     * @return
     */
    public int removeActParticipants(Integer colUserActivityId);

    /**
     * 查询评论回复列表
     * 
     * @param activityId
     * @return
     */
    public List<CommentReplyDTO> queryActComments(Integer activityId);

    /**
     * 回复活动评论
     * 
     * @param colActCommentId
     * @param colReplyContent
     * @param replyUserType
     * @param colCurReplyUserId
     * @param colCurReplyUserName
     * @return
     */
    public int createActCommentReply(Integer colActCommentId, String colReplyContent, Integer replyUserType,
            Integer colCurReplyUserId, String colCurReplyUserName);

    /**
     * 显示/屏蔽评论
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
     * 退款
     * 
     * @param colUserActivityId
     * @return
     */
    public int refund(Integer colUserActivityId, Integer colUserId, Integer colPaymentType, String colPayAccount,
            String colReceiverAccount, Double colMoney);

    /**
     * 根据参与id查询支付流水记录
     * 
     * @param colUserActivityId
     * @param colPayType
     * @return
     */
    public PaymentHistoryDTO getPaymentHistoryByUserActivityId(Integer colUserActivityId, Integer colAppUserId,
            Integer colPayType);

    /**
     * 收款
     * 
     * @param colUserActivityId
     * @param colUserId
     * @param colPaymentType
     * @param colPayAccount
     * @param colReceiverAccount
     * @param colMoney
     * @return
     */
    public int collection(Integer colUserActivityId, Integer colUserId, Integer colPaymentType, String colPayAccount,
            String colReceiverAccount, Double colMoney);

    public Activity getCostByActivityId(String colActivityId);

}
