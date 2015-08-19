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

import com.hexin.core.constant.Constants;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.OrgMember;
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.service.OrgMemberService;
import com.hexin.icp.util.WebUtil;

@Controller
@RequestMapping("/orgMember")
public class OrgMemberController {

    private static final Logger logger = LoggerFactory.getLogger(OrgMemberController.class);

    @Autowired
    private OrgMemberService    service;

    /**
     * 创建本机构成员
     * 
     * @return
     */
    @RequiresPermissions("myOrgMember:create")
    @RequestMapping(value = "createMyOrgMember")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object createMyOrgMember(@RequestParam(value = "positionId", required = true) Integer positionId,
            @RequestParam(value = "colUserName", required = true) String colUserName,
            @RequestParam(value = "colUserMobile", required = true) String colUserMobile,
            @RequestParam(value = "colUserCompany", required = false) String colUserCompany,
            @RequestParam(value = "colUserIndustry", required = false) String colUserIndustry,
            @RequestParam(value = "colUserJob", required = false) String colUserJob,
            @RequestParam(value = "colUserEmail", required = true) String colUserEmail,
            @RequestParam(value = "colUserAddress", required = true) String colUserAddress,
            @RequestParam(value = "colReceiveMsgFlag", required = false) Integer colReceiveMsgFlag,
            @RequestParam(value = "colFriendInvite", required = false) Integer colFriendInvite,
            @RequestParam(value = "colShowDetailInner", required = false) Integer colShowDetailInner,
            @RequestParam(value = "colShowDetailOutter", required = false) Integer colShowDetailOutter) {

        ReturnMessage rm = new ReturnMessage();
        AdminUser curUser = null;
        Integer orgId = null;
        Integer createBy = null;

        try {
            curUser = WebUtil.getLoginUser();
            orgId = curUser.getColOrgId();
            createBy = curUser.getAdminUserId();

            int code = service.createMyOrgMember(orgId, positionId, colUserName, colUserMobile, colUserCompany,
                    colUserIndustry, colUserJob, colUserEmail, colUserAddress, colReceiveMsgFlag, colFriendInvite,
                    colShowDetailInner, colShowDetailOutter, createBy);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
            rm.setMessage(Constants.ORG_MEMBER_CREATE_FAILED);
        } finally {
            return rm;
        }
    }

    //
    // /**
    // * 添加机构成员（关联已有appUser）
    // *
    // * @return
    // */
    // @RequiresPermissions("myOrgMember:addMyOrgMember")
    // @RequestMapping(value = "addMyOrgMember")
    // @ResponseBody
    // @SuppressWarnings("finally")
    // public Object addMyOrgMember(
    // @RequestParam(value = "userId", required = true) Integer userId,
    // @RequestParam(value = "positionId", required = false) Integer positionId,
    // @RequestParam(value = "colShowDetailInner", required = false) Integer
    // colShowDetailInner,
    // @RequestParam(value = "colShowDetailOutter", required = false) Integer
    // colShowDetailOutter) {
    // ReturnMessage rm = new ReturnMessage();
    // AdminUser curUser = null;
    // Integer orgId = null;
    // Integer createBy = null;
    //
    // try {
    //
    // curUser = WebUtil.getLoginUser();
    // orgId = curUser.getColOrgId();
    // createBy = curUser.getAdminUserId();
    //
    // int code = service.addMyOrgMember(userId, orgId, positionId,
    // colShowDetailInner, colShowDetailOutter, createBy);
    //
    // rm.setCode(code + "");
    // } catch (Exception e) {
    // logger.error(e.getMessage(), e);
    // rm.setCode(Constants.FAILED);
    // rm.setMessage(Constants.ORG_MEMBER_CREATE_FAILED);
    // } finally {
    // return rm;
    // }
    // }

    /**
     * 创建机构成员
     * 
     * @return
     */
    @RequiresPermissions("orgMember:create")
    @RequestMapping(value = "createOrgMember")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object createOrgMember(@RequestParam(value = "orgId", required = false) Integer[] orgIdArr,
            @RequestParam(value = "positionId", required = false) Integer[] positionIdArr,
            @RequestParam(value = "colUserName", required = true) String colUserName,
            @RequestParam(value = "colUserMobile", required = true) String colUserMobile,
            @RequestParam(value = "colUserCompany", required = false) String colUserCompany,
            @RequestParam(value = "colUserIndustry", required = false) String colUserIndustry,
            @RequestParam(value = "colUserJob", required = false) String colUserJob,
            @RequestParam(value = "colUserEmail", required = true) String colUserEmail,
            @RequestParam(value = "colUserAddress", required = true) String colUserAddress,
            @RequestParam(value = "colReceiveMsgFlag", required = false) Integer colReceiveMsgFlag,
            @RequestParam(value = "colFriendInvite", required = false) Integer colFriendInvite,
            @RequestParam(value = "colShowDetailInner", required = false) Integer colShowDetailInner,
            @RequestParam(value = "colShowDetailOutter", required = false) Integer colShowDetailOutter) {

        ReturnMessage rm = new ReturnMessage();

        try {
            boolean isOrgPosParamLengthSuit = (orgIdArr != null && positionIdArr != null && orgIdArr.length == positionIdArr.length);
            if (!isOrgPosParamLengthSuit) { // 如果机构id跟职务id数组不匹配，值置空
                rm.setCode(Constants.FAILED);
                rm.setMessage(Constants.ORG_MEMBER_ORG_POS_NOT_SUITE);
                return rm;
            }

            AdminUser curUser = WebUtil.getLoginUser();
            Integer createBy = curUser.getAdminUserId();

            int code = service.createOrgMember(orgIdArr, positionIdArr, colUserName, colUserMobile, colUserCompany,
                    colUserIndustry, colUserJob, colUserEmail, colUserAddress, colReceiveMsgFlag, colFriendInvite,
                    colShowDetailInner, colShowDetailOutter, createBy);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
            rm.setMessage(Constants.ORG_MEMBER_CREATE_FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 添加机构成员（关联已有appUser）
     * 
     * @return
     */
    @RequiresPermissions("orgMember:addOrgMember")
    @RequestMapping(value = "addOrgMember")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object addOrgMember(@RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "orgId", required = false) Integer[] orgIdArr,
            @RequestParam(value = "positionId", required = false) Integer[] positionIdArr,
            @RequestParam(value = "colShowDetailInner", required = false) Integer colShowDetailInner,
            @RequestParam(value = "colShowDetailOutter", required = false) Integer colShowDetailOutter) {

        ReturnMessage rm = new ReturnMessage();
        AdminUser curUser = null;
        Integer createBy = null;

        try {

            curUser = WebUtil.getLoginUser();
            createBy = curUser.getAdminUserId();

            int code = service.addOrgMember(userId, orgIdArr, positionIdArr, colShowDetailInner, colShowDetailOutter,
                    createBy);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
            rm.setMessage(Constants.ORG_MEMBER_CREATE_FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 修改机构成员
     * 
     * @return
     */
    @RequiresPermissions("orgMember:update")
    @RequestMapping(value = "updateOrgMember")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object updateOrgMember(@RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "orgId", required = false) Integer[] orgIdArr,
            @RequestParam(value = "positionId", required = false) Integer[] positionIdArr,
            @RequestParam(value = "colUserName", required = true) String colUserName,
            @RequestParam(value = "colUserMobile", required = true) String colUserMobile,
            @RequestParam(value = "colUserCompany", required = false) String colUserCompany,
            @RequestParam(value = "colUserIndustry", required = false) String colUserIndustry,
            @RequestParam(value = "colUserJob", required = false) String colUserJob,
            @RequestParam(value = "colUserEmail", required = true) String colUserEmail,
            @RequestParam(value = "colUserAddress", required = true) String colUserAddress,
            @RequestParam(value = "colReceiveMsgFlag", required = false) Integer colReceiveMsgFlag,
            @RequestParam(value = "colFriendInvite", required = false) Integer colFriendInvite,
            @RequestParam(value = "colShowDetailInner", required = false) Integer colShowDetailInner,
            @RequestParam(value = "colShowDetailOutter", required = false) Integer colShowDetailOutter) {

        ReturnMessage rm = new ReturnMessage();

        try {
            boolean isOrgPosParamLengthSuit = (orgIdArr != null && positionIdArr != null && orgIdArr.length == positionIdArr.length);
            if (!isOrgPosParamLengthSuit) { // 如果机构id跟职务id数组不匹配，值置空
                rm.setCode(Constants.FAILED);
                rm.setMessage(Constants.ORG_MEMBER_ORG_POS_NOT_SUITE);
                return rm;
            }

            AdminUser curUser = WebUtil.getLoginUser();
            Integer updateBy = curUser.getAdminUserId();

            int code = service.updateOrgMember(userId, orgIdArr, positionIdArr, colUserName, colUserMobile,
                    colUserCompany, colUserIndustry, colUserJob, colUserEmail, colUserAddress, colReceiveMsgFlag,
                    colFriendInvite, colShowDetailInner, colShowDetailOutter, updateBy);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
            rm.setMessage(Constants.ORG_MEMBER_UPDATE_FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 修改本机构成员
     * 
     * @return
     */
    @RequiresPermissions("myOrgMember:update")
    @RequestMapping(value = "updateMyOrgMember")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object updateMyOrgMember(
            @RequestParam(value = "orgMemInternalId", required = true) Integer orgMemInternalId,
            @RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "orgId", required = false) Integer orgId,
            @RequestParam(value = "positionId", required = false) Integer positionId,
            @RequestParam(value = "colUserName", required = true) String colUserName,
            @RequestParam(value = "colUserMobile", required = true) String colUserMobile,
            @RequestParam(value = "colUserCompany", required = false) String colUserCompany,
            @RequestParam(value = "colUserIndustry", required = false) String colUserIndustry,
            @RequestParam(value = "colUserJob", required = false) String colUserJob,
            @RequestParam(value = "colUserEmail", required = true) String colUserEmail,
            @RequestParam(value = "colUserAddress", required = true) String colUserAddress,
            @RequestParam(value = "colReceiveMsgFlag", required = false) Integer colReceiveMsgFlag,
            @RequestParam(value = "colFriendInvite", required = false) Integer colFriendInvite,
            @RequestParam(value = "colShowDetailInner", required = false) Integer colShowDetailInner,
            @RequestParam(value = "colShowDetailOutter", required = false) Integer colShowDetailOutter) {

        ReturnMessage rm = new ReturnMessage();
        AdminUser curUser = null;
        Integer updateBy = null;

        try {

            curUser = WebUtil.getLoginUser();
            updateBy = curUser.getAdminUserId();

            int code = service.updateMyOrgMember(orgMemInternalId, userId, orgId, positionId, colUserName,
                    colUserMobile, colUserCompany, colUserIndustry, colUserJob, colUserEmail, colUserAddress,
                    colReceiveMsgFlag, colFriendInvite, colShowDetailInner, colShowDetailOutter, updateBy);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
            rm.setMessage(Constants.ORG_MEMBER_UPDATE_FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 查询本机构成员
     * 
     * @return
     */
    @RequiresPermissions("myOrgMember:query")
    @RequestMapping(value = "queryMyOrgMembers")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryMyOrgMembers(PageCondition pageCondition,
            @RequestParam(value = "colUserMobile", required = false) String colUserMobile) {

        Page<OrgMember> page = null;

        try {
            AdminUser curUser = WebUtil.getLoginUser();
            Integer orgId = curUser.getColOrgId();

            page = service.queryMyOrgMembers(pageCondition, orgId, colUserMobile);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return page;
        }
    }

    /**
     * 查询成员
     * 
     * @return
     */
    @RequiresPermissions("orgMember:query")
    @RequestMapping(value = "queryOrgMembers")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryOrgMembers(@RequestParam(value = "colUserMobile", required = false) String colUserMobile) {

        List<OrgMember> list = null;

        try {
            AdminUser curUser = WebUtil.getLoginUser();
            Integer adminUserId = curUser.getAdminUserId();
            String adminUserType = curUser.getColAdminUserType();
            Integer orgId = curUser.getColOrgId();
            list = service.queryOrgMembers(adminUserId, adminUserType, orgId, colUserMobile);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

}
