package com.hexin.icp.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hexin.core.constant.Constants;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.ActParticipants;
import com.hexin.icp.bean.Activity;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.CommentReplyDTO;
import com.hexin.icp.bean.PaymentHistoryDTO;
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.service.ActivityService;
import com.hexin.icp.util.WebUtil;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService     service;

    /**
     * 添加活动
     * 
     * @return
     */
    @RequiresPermissions("activity:create")
    @RequestMapping(value = "createActivity")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object createActivity(@RequestParam(value = "colActivityTitle", required = true) String colActivityTitle,
            @RequestParam(value = "colActivityBrief", required = false) String colActivityBrief,
            @RequestParam(value = "colActivityStartTime", required = false) String colActivityStartTime,
            @RequestParam(value = "colActivityEndTime", required = false) String colActivityEndTime,
            @RequestParam(value = "colActivityType", required = false) String colActivityType,
            @RequestParam(value = "colActivityIssueOrgId", required = false) Integer colActivityIssueOrgId,
            @RequestParam(value = "colActivityOrgnizer", required = false) String colActivityOrgnizer,
            @RequestParam(value = "colActivityPlace", required = false) String colActivityPlace,
            @RequestParam(value = "colActivityPlaceLatitude", required = false) Float colActivityPlaceLatitude,
            @RequestParam(value = "colActivityPlaceLongitude", required = false) Float colActivityPlaceLongitude,
            @RequestParam(value = "colActivityBackground", required = false) String colActivityBackground,
            @RequestParam(value = "colActivityAgenda", required = false) String colActivityAgenda,
            @RequestParam(value = "colActivityGuest", required = false) String colActivityGuest,
            @RequestParam(value = "colActivityContent", required = false) String colActivityContent,
            @RequestParam(value = "colActivityIndexId", required = false) Integer colActivityIndexId,
            @RequestParam(value = "colActivityCost", required = false) Float colActivityCost,
            @RequestParam(value = "colActivitySharableFlag", required = false) Integer colActivitySharableFlag,
            @RequestParam(value = "colActivityTitleImgUrl", required = false) String colActivityTitleImgUrl,
            @RequestParam(value = "colActivityBriefImgUrl", required = false) String colActivityBriefImgUrl,
            @RequestParam(value = "colActivityAuditableFlag", required = false) Integer colActivityAuditableFlag,
            @RequestParam(value = "colActivityNeedPayFlag", required = false) Integer colActivityNeedPayFlag,
            @RequestParam(value = "colActivityEnrollDeadline", required = false) String colActivityEnrollDeadline) {

        ReturnMessage rm = new ReturnMessage();

        try {
            AdminUser curUser = WebUtil.getLoginUser();
            Integer adminUserId = curUser.getAdminUserId();
            String adminUserType = curUser.getColAdminUserType();

            int code = service.createActivity(adminUserId, adminUserType, colActivityIssueOrgId, colActivityTitle,
                    colActivityTitleImgUrl, colActivityBrief, colActivityBriefImgUrl, colActivityAuditableFlag,
                    colActivityNeedPayFlag, colActivityEnrollDeadline, colActivityStartTime, colActivityEndTime,
                    colActivityType, colActivityBackground, colActivityAgenda, colActivityGuest, colActivityOrgnizer,
                    colActivityPlace, colActivityPlaceLatitude, colActivityPlaceLongitude, colActivityContent,
                    colActivityIndexId, colActivityCost, colActivitySharableFlag);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 添加活动
     * 
     * @return
     */
    @RequiresPermissions("activity:update")
    @RequestMapping(value = "updateActivity")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object updateActivity(@RequestParam(value = "activityId", required = true) Integer activityId,
            @RequestParam(value = "colActivityTitle", required = true) String colActivityTitle,
            @RequestParam(value = "colActivityBrief", required = false) String colActivityBrief,
            @RequestParam(value = "actTitleImgId", required = false) Integer actTitleImgId,
            @RequestParam(value = "activityTitleImg", required = false) MultipartFile activityTitleImg,
            @RequestParam(value = "colActivityStartTime", required = false) String colActivityStartTime,
            @RequestParam(value = "colActivityEndTime", required = false) String colActivityEndTime,
            @RequestParam(value = "colActivityType", required = false) String colActivityType,
            @RequestParam(value = "colActivityIssueOrgId", required = false) Integer colActivityIssueOrgId,
            @RequestParam(value = "colActivityOrgnizer", required = false) String colActivityOrgnizer,
            @RequestParam(value = "colActivityPlace", required = false) String colActivityPlace,
            @RequestParam(value = "colActivityPlaceLatitude", required = false) Float colActivityPlaceLatitude,
            @RequestParam(value = "colActivityPlaceLongitude", required = false) Float colActivityPlaceLongitude,
            @RequestParam(value = "colActivityBackground", required = false) String colActivityBackground,
            @RequestParam(value = "colActivityAgenda", required = false) String colActivityAgenda,
            @RequestParam(value = "colActivityGuest", required = false) String colActivityGuest,
            @RequestParam(value = "colActivityContent", required = false) String colActivityContent,
            @RequestParam(value = "colActivityIndexId", required = false) Integer colActivityIndexId,
            @RequestParam(value = "colActivityCost", required = false) Float colActivityCost,
            @RequestParam(value = "colActivitySharableFlag", required = false) Integer colActivitySharableFlag,
            @RequestParam(value = "colActivityTitleImgUrl", required = false) String colActivityTitleImgUrl,
            @RequestParam(value = "colActivityBriefImgUrl", required = false) String colActivityBriefImgUrl,
            @RequestParam(value = "colActivityAuditableFlag", required = false) Integer colActivityAuditableFlag,
            @RequestParam(value = "colActivityNeedPayFlag", required = false) Integer colActivityNeedPayFlag,
            @RequestParam(value = "colActivityEnrollDeadline", required = false) String colActivityEnrollDeadline) {

        ReturnMessage rm = new ReturnMessage();

        try {
            AdminUser curUser = WebUtil.getLoginUser();
            Integer adminUserId = curUser.getAdminUserId();
            String adminUserType = curUser.getColAdminUserType();
            Integer orgId = curUser.getColOrgId();

            int code = service.updateActivity(adminUserId, adminUserType, orgId, activityId, colActivityTitle,
                    colActivityBrief, actTitleImgId, activityTitleImg, colActivityStartTime, colActivityEndTime,
                    colActivityType, colActivityIssueOrgId, colActivityBackground, colActivityAgenda, colActivityGuest,
                    colActivityOrgnizer, colActivityPlace, colActivityPlaceLatitude, colActivityPlaceLongitude,
                    colActivityContent, colActivityIndexId, colActivityCost, colActivitySharableFlag,
                    colActivityTitleImgUrl, colActivityBriefImgUrl, colActivityAuditableFlag, colActivityNeedPayFlag,
                    colActivityEnrollDeadline);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 删除新闻
     *
     * @return
     */
    @RequiresPermissions("activity:remove")
    @RequestMapping(value = "removeActivity")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object removeActivity(@RequestParam(value = "activityId", required = true) Integer activityId) {

        ReturnMessage rm = new ReturnMessage();

        try {
            AdminUser curUser = WebUtil.getLoginUser();
            Integer adminUserId = curUser.getAdminUserId();

            int code = service.removeActivity(adminUserId, activityId);
            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 查询活动列表
     * 
     * @return
     */
    @RequiresPermissions("activity:query")
    @RequestMapping(value = "queryActivities")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryActivities(PageCondition pageCondition,
            @RequestParam(value = "colActivityTitle", required = false) String colActivityTitle) {

        Page<Activity> result = null;

        try {
            AdminUser curUser = WebUtil.getLoginUser();
            Integer adminUserId = curUser.getAdminUserId();
            String adminUserType = curUser.getColAdminUserType();
            Integer orgId = curUser.getColOrgId();

            result = service.queryActivities(pageCondition, adminUserId, adminUserType, orgId, colActivityTitle);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return result;
        }
    }

    /**
     * 查询活动
     * 
     * @return
     */
    @RequiresPermissions("activity:query")
    @RequestMapping(value = "getCostByActivityId")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object getCostByActivityId(@RequestParam(value = "colActivityId", required = true) String colActivityId) {

        ReturnMessage rm = new ReturnMessage();

        try {
            Activity act = service.getCostByActivityId(colActivityId);

            rm.setResult(act);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return rm;
        }
    }

    /**
     * 查询活动列表
     * 
     * @return
     */
    @RequiresPermissions("activity:showActParticipants")
    @RequestMapping(value = "queryActParticipants")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryActParticipants(@RequestParam(value = "activityId", required = true) Integer activityId) {

        List<ActParticipants> list = null;

        try {

            list = service.queryActParticipants(activityId);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 根据参与id查询支付流水记录
     * 
     * @return
     */
    @RequiresPermissions("activity:showActParticipants")
    @RequestMapping(value = "getPaymentHistoryByUserActivityId")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object getPaymentHistoryByUserActivityId(
            @RequestParam(value = "colUserActivityId", required = true) Integer colUserActivityId,
            @RequestParam(value = "colAppUserId", required = true) Integer colAppUserId,
            @RequestParam(value = "colPayType", required = true) Integer colPayType) {

        PaymentHistoryDTO result = null;

        try {

            result = service.getPaymentHistoryByUserActivityId(colUserActivityId, colAppUserId, colPayType);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (result == null) {
                result = new PaymentHistoryDTO();
            }

            return result;
        }
    }

    /**
     * 审核活动参与通过
     * 
     * @return
     */
    @RequiresPermissions("activity:showActParticipants")
    @RequestMapping(value = "auditActJoinPass")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object auditActJoinPass(@RequestParam(value = "colUserActivityId", required = true) Integer colUserActivityId) {

        ReturnMessage rm = new ReturnMessage();

        try {

            rm = service.auditActJoinPass(colUserActivityId);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 审核活动参与不通过
     * 
     * @return
     */
    @RequiresPermissions("activity:showActParticipants")
    @RequestMapping(value = "auditActJoinReject")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object auditActJoinReject(
            @RequestParam(value = "colUserActivityId", required = true) Integer colUserActivityId) {

        ReturnMessage rm = new ReturnMessage();

        try {

            rm = service.auditActJoinReject(colUserActivityId);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 删除活动参与者
     * 
     * @return
     */
    @RequiresPermissions("activity:showActParticipants")
    @RequestMapping(value = "removeActParticipants")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object removeActParticipants(
            @RequestParam(value = "colUserActivityId", required = true) Integer colUserActivityId) {

        ReturnMessage rm = new ReturnMessage();
        int code;

        try {

            code = service.removeActParticipants(colUserActivityId);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 收款
     * 
     * @return
     */
    @RequiresPermissions("activity:showActParticipants")
    @RequestMapping(value = "collection")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object collection(@RequestParam(value = "colUserActivityId", required = true) Integer colUserActivityId,
            @RequestParam(value = "colUserId", required = true) Integer colUserId,
            @RequestParam(value = "colPaymentType", required = true) Integer colPaymentType,
            @RequestParam(value = "colPayAccount", required = true) String colPayAccount,
            @RequestParam(value = "colReceiverAccount", required = true) String colReceiverAccount,
            @RequestParam(value = "colMoney", required = true) Double colMoney) {

        ReturnMessage rm = new ReturnMessage();
        int code;

        try {

            code = service.collection(colUserActivityId, colUserId, colPaymentType, colPayAccount, colReceiverAccount,
                    colMoney);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 退款
     * 
     * @return
     */
    @RequiresPermissions("activity:showActParticipants")
    @RequestMapping(value = "refund")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object refund(@RequestParam(value = "colUserActivityId", required = true) Integer colUserActivityId,
            @RequestParam(value = "colUserId", required = true) Integer colUserId,
            @RequestParam(value = "colPaymentType", required = true) Integer colPaymentType,
            @RequestParam(value = "colPayAccount", required = true) String colPayAccount,
            @RequestParam(value = "colReceiverAccount", required = true) String colReceiverAccount,
            @RequestParam(value = "colMoney", required = true) Double colMoney) {

        ReturnMessage rm = new ReturnMessage();
        int code;

        try {

            code = service.refund(colUserActivityId, colUserId, colPaymentType, colPayAccount, colReceiverAccount,
                    colMoney);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 查询评论回复列表
     * 
     * @return
     */
    @RequiresPermissions("activity:showActComments")
    @RequestMapping(value = "queryActComments")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryActComments(@RequestParam(value = "activityId", required = true) Integer activityId) {

        List<CommentReplyDTO> list = null;

        try {

            list = service.queryActComments(activityId);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 回复评论
     * 
     * @return
     */
    @RequiresPermissions("activity:showActComments")
    @RequestMapping(value = "createActCommentReply")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object createActCommentReply(
            @RequestParam(value = "colActCommentId", required = true) Integer colActCommentId,
            @RequestParam(value = "colReplyContent", required = true) String colReplyContent) {

        ReturnMessage rm = new ReturnMessage();
        int code;
        AdminUser curUser;
        Integer replyUserType = 2;
        Integer colCurReplyUserId;
        String colCurReplyUserName;

        try {
            curUser = WebUtil.getLoginUser();
            colCurReplyUserId = curUser.getAdminUserId();
            colCurReplyUserName = curUser.getColAdminUsername();

            code = service.createActCommentReply(colActCommentId, colReplyContent, replyUserType, colCurReplyUserId,
                    colCurReplyUserName);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 更新评论回复
     * 
     * @return
     */
    @RequiresPermissions("activity:showActComments")
    @RequestMapping(value = "updateActCommentReply")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object updateActCommentReply(@RequestParam(value = "replyId", required = true) Integer replyId,
            @RequestParam(value = "colReplyContent", required = true) String colReplyContent) {

        ReturnMessage rm = new ReturnMessage();
        int code;

        try {
            code = service.updateActCommentReply(replyId, colReplyContent);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 显示/屏蔽评论
     * 
     * @return
     */
    @RequiresPermissions("activity:showActComments")
    @RequestMapping(value = "updateActCommentShowStatus")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object updateActCommentShowStatus(
            @RequestParam(value = "colActCommentId", required = true) Integer colActCommentId,
            @RequestParam(value = "colCommentShowStatus", required = true) Integer colCommentShowStatus) {

        ReturnMessage rm = new ReturnMessage();
        int code;

        try {

            code = service.updateActCommentShowStatus(colActCommentId, colCommentShowStatus);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

}
