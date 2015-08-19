package com.hexin.icp.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hexin.core.constant.Constants;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.ActParticipants;
import com.hexin.icp.bean.Activity;
import com.hexin.icp.bean.CommentReply;
import com.hexin.icp.bean.CommentReplyDTO;
import com.hexin.icp.bean.Img;
import com.hexin.icp.bean.PaymentHistoryDTO;
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.dao.ActivityDao;
import com.hexin.icp.dao.ImgDao;
import com.hexin.icp.service.ActivityService;
import com.hexin.icp.util.FileUtil;

@Service
@Transactional(value = "transactionManager")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao dao;
    @Autowired
    private ImgDao      imgDao;

    @Override
    public Page<Activity> queryActivities(PageCondition pageCondition, Integer adminUserId, String adminUserType,
            Integer orgId, String colActivityTitle) throws SecurityException, IllegalArgumentException, SQLException,
            InstantiationException, IllegalAccessException, NoSuchFieldException, IOException {

        Page<Activity> result = null;

        if ("1".equals(adminUserType)) {
            // 机构管理用户只返回本机构发布的活动
            result = dao.findOrgActsByOrgId(pageCondition, orgId, colActivityTitle);
        } else if ("2".equals(adminUserType)) {
            // 系统管理用户返回所有的活动
            result = dao.findAllActs(pageCondition, colActivityTitle);
        }

        return result;
    }

    @Override
    public int createActivity(Integer adminUserId, String adminUserType, Integer colActivityIssueOrgId,
            String colActivityTitle, String colActivityTitleImgUrl, String colActivityBrief,
            String colActivityBriefImgUrl, Integer colActivityAuditableFlag, Integer colActivityNeedPayFlag,
            String colActivityEnrollDeadline, String colActivityStartTime, String colActivityEndTime,
            String colActivityType, String colActivityBackground, String colActivityAgenda, String colActivityGuest,
            String colActivityOrgnizer, String colActivityPlace, Float colActivityPlaceLatitude,
            Float colActivityPlaceLongitude, String colActivityContent, Integer colActivityIndexId,
            Float colActivityCost, Integer colActivitySharableFlag) throws FileNotFoundException,
            NoSuchAlgorithmException, IOException, MyException {

        Integer colCreateBy = adminUserId;
        String colActivityIssueOrgType = null;
        // Img newImg = null;
        // String newImgPath = null;
        // String newCompressImgPath = null;
        // Integer newImgId = null;
        int affectedImgRows;
        int affectedRelRows;

        if ("1".equals(adminUserType)) {
            colActivityIssueOrgType = "2"; // 机构发布
        } else if ("2".equals(adminUserType)) {
            colActivityIssueOrgType = "3"; // 系统发布
        }

        // // 上传并更新图片记录
        // if (activityTitleImg != null && !activityTitleImg.isEmpty()) {
        // // 上传头像
        // newImg = FileUtil.uploadOriginalAndCompress(activityTitleImg);
        //
        // if (newImg != null) { // 更新图片记录
        // // 创建图片记录
        // newImgPath = newImg.getColImgPath();
        // newCompressImgPath = newImg.getColImgCompressPath();
        // newImgId = imgDao.createImg(newImgPath, newCompressImgPath);
        // }
        // }

        // 上传和页面
        String cntDetailUrl = FileUtil.uploadActivityH5Page(colActivityStartTime, colActivityEndTime, colActivityPlace,
                colActivityOrgnizer, colActivityTitle, colActivityBriefImgUrl, colActivityBackground,
                colActivityAgenda, colActivityGuest, colActivityContent);

        // 创建t_activity记录
        Integer activityId = dao.createActivity(colActivityIssueOrgType, colActivityIssueOrgId, colActivityTitle,
                colActivityTitleImgUrl, colActivityBrief, colActivityBriefImgUrl, colActivityAuditableFlag,
                colActivityNeedPayFlag, colActivityEnrollDeadline, colActivityStartTime, colActivityEndTime,
                colActivityType, colActivityBackground, colActivityAgenda, colActivityGuest, colActivityOrgnizer,
                colActivityPlace, colActivityPlaceLatitude, colActivityPlaceLongitude, colActivityContent,
                cntDetailUrl, colActivityIndexId, colActivityCost, colActivitySharableFlag, colCreateBy);

        // if (newImgId != null && activityId != null) {
        // // 创建t_img_rel关联记录（活动标题图片）
        // String colRelType = "4";
        // affectedRelRows = imgDao.createImgRel(newImgId, activityId, colRelType);
        // }

        return 1;
    }

    @Override
    public int updateActivity(Integer adminUserId, String adminUserType, Integer orgId, Integer activityId,
            String colActivityTitle, String colActivityBrief, Integer actTitleImgId, MultipartFile activityTitleImg,
            String colActivityStartTime, String colActivityEndTime, String colActivityType,
            Integer colActivityIssueOrgId, String colActivityBackground, String colActivityAgenda,
            String colActivityGuest, String colActivityOrgnizer, String colActivityPlace,
            Float colActivityPlaceLatitude, Float colActivityPlaceLongitude, String colActivityContent,
            Integer colActivityIndexId, Float colActivityCost, Integer colActivitySharableFlag,
            String colActivityTitleImgUrl, String colActivityBriefImgUrl, Integer colActivityAuditableFlag,
            Integer colActivityNeedPayFlag, String colActivityEnrollDeadline) throws NoSuchAlgorithmException,
            IOException, MyException {

        Integer colUpdateBy = adminUserId;
        // Img oldImg = null;
        // Img newImg = null;
        // String oldImgPath = null;
        // String oldCompressImgPath = null;
        // String newImgPath = null;
        // String newCompressImgPath = null;
        // Integer newImgId = null;
        // int affectedImgRows;
        int affectedRelRows;

        // 上传并更新图片记录
        // if (activityTitleImg != null && !activityTitleImg.isEmpty()) {
        // // 上传头像
        // newImg = FileUtil.uploadOriginalAndCompress(activityTitleImg);
        //
        // if (newImg != null && actTitleImgId != null) { // 更新图片记录
        // // 更新图片记录
        // newImgPath = newImg.getColImgPath();
        // newCompressImgPath = newImg.getColImgCompressPath();
        // affectedImgRows = imgDao.updateImg(actTitleImgId, newImgPath, newCompressImgPath);
        //
        // // 删除原图片文件
        // oldImg = imgDao.getImgByImgId(actTitleImgId);
        // oldImgPath = oldImg.getColImgPath();
        // oldCompressImgPath = oldImg.getColImgCompressPath();
        // FileUtil.deleteRemoteFile(oldImgPath, oldCompressImgPath);
        //
        // } else if (newImg != null && actTitleImgId == null) { // 创建图片记录
        // // 创建图片记录
        // newImgPath = newImg.getColImgPath();
        // newCompressImgPath = newImg.getColImgCompressPath();
        // newImgId = imgDao.createImg(newImgPath, newCompressImgPath);
        //
        // // 创建t_img_rel关联记录（活动标题图片）
        // String colRelType = "4";
        // affectedRelRows = imgDao.createImgRel(newImgId, activityId, colRelType);
        // }
        // } else if (actTitleImgId != null) {
        // oldImg = imgDao.getImgByImgId(actTitleImgId);
        // newImgPath = oldImg.getColImgPath();
        // }

        // 上传和页面
        String cntDetailUrl = FileUtil.uploadActivityH5Page(colActivityStartTime, colActivityEndTime, colActivityPlace,
                colActivityOrgnizer, colActivityTitle, colActivityBriefImgUrl, colActivityBackground,
                colActivityAgenda, colActivityGuest, colActivityContent);

        // 更新t_activity记录
        int affectedActRows = dao.updateActivity(activityId, colActivityTitle, colActivityBrief, colActivityStartTime,
                colActivityEndTime, colActivityType, colActivityIssueOrgId, colActivityBackground, colActivityAgenda,
                colActivityGuest, colActivityOrgnizer, colActivityPlace, colActivityPlaceLatitude,
                colActivityPlaceLongitude, colActivityContent, cntDetailUrl, colActivityIndexId, colActivityCost,
                colActivitySharableFlag, colUpdateBy, colActivityTitleImgUrl, colActivityBriefImgUrl,
                colActivityAuditableFlag, colActivityNeedPayFlag, colActivityEnrollDeadline);

        return 1;
    }

    @Override
    public int removeActivity(Integer adminUserId, Integer activityId) {

        int affetedRows = dao.updateActivityDelFlag(adminUserId, activityId);

        return affetedRows;
    }

    @Override
    public List<ActParticipants> queryActParticipants(Integer activityId) {

        List<ActParticipants> list = null;

        list = dao.queryActParticipants(activityId, null, null);

        return list;
    }

    @Override
    public ReturnMessage auditActJoinPass(Integer colUserActivityId) {

        ReturnMessage rm = new ReturnMessage();
        ActParticipants actPartcipants = null;
        String colAuthStatus = null;
        Integer actId = null;
        Integer appUserId = null;
        List<ActParticipants> joinedParticipants = null;
        int affectedRows;

        // 验证用户尚未参加该活动（t_user_act包括记录col_auth_status=1）
        actPartcipants = dao.getActPartcipantsById(colUserActivityId);
        if (actPartcipants != null) {
            actId = actPartcipants.getColActivityId();
            appUserId = actPartcipants.getColUserId();

            // 查找已经参加活动的关联记录
            joinedParticipants = dao.queryActParticipants(actId, appUserId, "1");

            if (!CollectionUtils.isEmpty(joinedParticipants)) {
                rm.setCode(Constants.ORG_ACT_VERI_ALREADY_JOINED);
                return rm;
            }
        }

        // 更新t_user_act审核状态col_auth_status=1（已确认）
        colAuthStatus = "1";
        affectedRows = dao.updateActParticipantsAuthStatus(colUserActivityId, colAuthStatus);

        rm.setCode(affectedRows + "");

        return rm;
    }

    @Override
    public ReturnMessage auditActJoinReject(Integer colUserActivityId) {

        ReturnMessage rm = new ReturnMessage();
        ActParticipants actPartcipants = null;
        String colAuthStatus = null;
        Integer actId = null;
        Integer appUserId = null;
        List<ActParticipants> joinedParticipants = null;
        int affectedRows;

        // 验证用户尚未参加该活动（t_user_act包括记录col_auth_status=1）
        actPartcipants = dao.getActPartcipantsById(colUserActivityId);
        if (actPartcipants != null) {
            actId = actPartcipants.getColActivityId();
            appUserId = actPartcipants.getColUserId();

            // 查找已经参加活动的关联记录
            joinedParticipants = dao.queryActParticipants(actId, appUserId, "1");

            if (!CollectionUtils.isEmpty(joinedParticipants)) {
                rm.setCode(Constants.ORG_ACT_VERI_ALREADY_JOINED);
                return rm;
            }
        }

        // 更新t_user_act审核状态col_auth_status=2（已拒绝）
        colAuthStatus = "2";
        affectedRows = dao.updateActParticipantsAuthStatus(colUserActivityId, colAuthStatus);

        rm.setCode(affectedRows + "");

        return rm;
    }

    @Override
    public int removeActParticipants(Integer colUserActivityId) {

        int affectedRows;

        affectedRows = dao.deleteActParticipants(colUserActivityId);

        return affectedRows;
    }

    @Override
    public List<CommentReplyDTO> queryActComments(Integer activityId) {

        List<CommentReplyDTO> result = null;
        List<CommentReply> list = null;

        list = dao.queryActComments(activityId);

        result = format4queryActComments(list);

        return result;
    }

    private List<CommentReplyDTO> format4queryActComments(List<CommentReply> list) {

        List<CommentReplyDTO> result = null;
        int replyIndex;

        if (CollectionUtils.isEmpty(list)) {
            return result;
        }

        replyIndex = 1;
        result = new ArrayList<CommentReplyDTO>();
        for (CommentReply dto : list) {
            Integer colActCommentId = dto.getColActCommentId();
            String colUserName = dto.getColUserName();
            String colCommentContent = dto.getColCommentContent();
            Integer colCommentShowStatus = dto.getColCommentShowStatus();
            Date colCommentCreateTime = dto.getColCommentCreateTime();

            Integer replyId = dto.getReplyId();

            // 添加评论记录
            CommentReplyDTO commentDto = new CommentReplyDTO();
            commentDto.setId(colActCommentId);
            commentDto.setType(1);
            commentDto.setColUserName(colUserName);
            commentDto.setContent(colCommentContent);
            commentDto.setShowStatus(colCommentShowStatus);
            commentDto.setCreateTime(colCommentCreateTime);
            commentDto.setReplyStatus(0);
            commentDto.setRemark("评论" + replyIndex);
            result.add(commentDto);

            // 如果恢复不为空，则新增回复记录到评论列表当中
            if (replyId != null) {
                commentDto.setReplyStatus(1);

                String colCurReplyUserName = dto.getColCurReplyUserName();
                String colReplyContent = dto.getColReplyContent();
                Date colReplyCreateTime = dto.getColReplyCreateTime();

                CommentReplyDTO replyDto = new CommentReplyDTO();
                replyDto.setId(replyId);
                replyDto.setType(2);
                replyDto.setColCurReplyUserName(colCurReplyUserName);
                replyDto.setContent(colReplyContent);
                replyDto.setCreateTime(colReplyCreateTime);
                replyDto.setRemark("回复评论" + replyIndex);

                result.add(replyDto);
            }

            replyIndex++;
        }

        return result;
    }

    @Override
    public int createActCommentReply(Integer colActCommentId, String colReplyContent, Integer replyUserType,
            Integer colCurReplyUserId, String colCurReplyUserName) {

        int affectedRows = 0;

        affectedRows = dao.insertActCommentReply(colActCommentId, colReplyContent, replyUserType, colCurReplyUserId,
                colCurReplyUserName);

        return affectedRows;
    }

    @Override
    public int updateActCommentShowStatus(Integer colActCommentId, Integer colCommentShowStatus) {

        int affectedRows = 0;

        affectedRows = dao.updateActCommentShowStatus(colActCommentId, colCommentShowStatus);

        return affectedRows;
    }

    @Override
    public int updateActCommentReply(Integer replyId, String colReplyContent) {

        int affectedRows = 0;

        affectedRows = dao.updateActCommentReply(replyId, colReplyContent);

        return affectedRows;
    }

    @Override
    public int collection(Integer colUserActivityId, Integer colUserId, Integer colPaymentType, String colPayAccount,
            String colReceiverAccount, Double colMoney) {

        int affectedPayHistoryRows = 0;
        int affectedUserActRows = 0;
        Integer colPayType = 1;
        Integer colPaymentStatus = 1;

        // 1、生成支付流水记录
        affectedPayHistoryRows = dao.insertPayHistory(colUserActivityId, colUserId, colPaymentType, colPayAccount,
                colReceiverAccount, colMoney, colPayType);

        // 2、修改参与记录对应支付状态
        affectedUserActRows = dao.updateUserActPayStatus(colUserActivityId, colPaymentStatus);

        return 1;
    }

    @Override
    public int refund(Integer colUserActivityId, Integer colUserId, Integer colPaymentType, String colPayAccount,
            String colReceiverAccount, Double colMoney) {

        int affectedPayHistoryRows = 0;
        int affectedUserActRows = 0;
        Integer colPayType = 2;
        Integer colPaymentStatus = 2;

        // 1、生成支付流水记录
        affectedPayHistoryRows = dao.insertPayHistory(colUserActivityId, colUserId, colPaymentType, colPayAccount,
                colReceiverAccount, colMoney, colPayType);

        // 2、修改参与记录对应支付状态
        affectedUserActRows = dao.updateUserActPayStatus(colUserActivityId, colPaymentStatus);

        return 1;
    }

    @Override
    public PaymentHistoryDTO getPaymentHistoryByUserActivityId(Integer colUserActivityId, Integer colAppUserId,
            Integer colPayType) {
        PaymentHistoryDTO result = null;

        result = dao.getPaymentHistoryByUserActivityId(colUserActivityId, colAppUserId, colPayType);

        return result;
    }

    @Override
    public Activity getCostByActivityId(String colActivityId) {
        Activity result = null;

        result = dao.getCostByActivityId(colActivityId);

        return result;
    }

}
