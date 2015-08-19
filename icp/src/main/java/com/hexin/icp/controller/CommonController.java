package com.hexin.icp.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
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
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.AppUser;
import com.hexin.icp.bean.Dict;
import com.hexin.icp.bean.Img;
import com.hexin.icp.bean.Index;
import com.hexin.icp.bean.Org;
import com.hexin.icp.bean.PaymentTypeDTO;
import com.hexin.icp.bean.Position;
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.bean.ZTreeDTO;
import com.hexin.icp.service.CommonService;
import com.hexin.icp.util.WebUtil;

/**
 * 通用类，一般用于select下拉框查询。一般不受权限控制。
 * 
 * @author Administrator
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private CommonService       service;

    /**
     * 添加新闻
     * 
     * @return
     */
    @RequestMapping(value = "uploadImg")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object uploadImg(@RequestParam(value = "imgFile", required = true) MultipartFile imgFile) {
        ReturnMessage rm = new ReturnMessage();

        try {
            Img img = service.uploadImg(imgFile);
            if (img != null && StringUtils.isNotEmpty(img.getColImgPath())) {
                rm.setCode(Constants.SUCCESSED);
                rm.setResult(img);
            } else {
                rm.setCode(Constants.FAILED);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 查询索引列表
     * 
     * @return
     */
    @RequestMapping(value = "queryIndexs4select")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryIndexs4select(@RequestParam(value = "colLabelClass", required = false) String colLabelClass) {
        List<Index> list = null;

        try {
            list = service.queryIndex4select(colLabelClass);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 查询t_dict列表
     * 
     * @return
     */
    @RequestMapping(value = "queryDict4select")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryDict4select(@RequestParam(value = "colDictClass", required = false) String colDictClass) {
        List<Dict> list = null;
        AdminUser curUser = null;
        String adminUserType = null;
        String colDictVal = null;

        try {
            curUser = WebUtil.getLoginUser();
            adminUserType = curUser.getColAdminUserType();

            // 查询活动面向类型时，如果当前与管理员为机构类型，则对应面向对象只能是“组织成员”
            if ("1".equals(adminUserType) && "4".equals(colDictClass)) {
                colDictVal = "1";
            }
            // 查询新闻面向类型时，如果当前与管理员为机构类型，则对应面向对象只能是“组织成员”
            if ("1".equals(adminUserType) && "6".equals(colDictClass)) {
                colDictVal = "1";
            }

            list = service.queryDict4select(colDictClass, colDictVal);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 查询t_dict列表对应colDictClass类型的记录中，最大的col_dict_val
     * 
     * @return
     */
    @RequestMapping(value = "getDictMaxVal")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object getDictMaxVal(@RequestParam(value = "colDictClass", required = true) String colDictClass) {
        ReturnMessage rm = new ReturnMessage();
        Integer colDictVal = null;

        try {

            colDictVal = service.getDictMaxVal(colDictClass);

            rm.setResult(colDictVal);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 查询机构列表
     * 
     * @return
     */
    @RequestMapping(value = "queryOrgs4select")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryOrgs4select() {
        List<Org> list = null;

        try {
            AdminUser curUser = WebUtil.getLoginUser();
            Integer adminUserId = curUser.getAdminUserId();
            String adminUserType = curUser.getColAdminUserType();
            Integer orgId = curUser.getColOrgId();

            list = service.queryOrgs4select(adminUserId, adminUserType, orgId);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 查询角色下拉框
     */
    @RequestMapping(value = "getRoleTree4select")
    @ResponseBody
    @SuppressWarnings("finally")
    public List<ZTreeDTO> getRoleTree4select(@RequestParam(value = "roleType", required = false) String roleType) {
        List<ZTreeDTO> list = null;

        try {

            list = service.getRoleTree4select(roleType);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 根据机构查询职位
     * 
     * @param orgId
     * @return
     */
    @RequestMapping(value = "queryPositionsByOrgId4select")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryPositionsByOrgId4select(@RequestParam(value = "orgId", required = false) Integer orgId) {
        List<Position> list = null;
        AdminUser curUser;

        try {

            if (orgId == null) {
                curUser = WebUtil.getLoginUser();
                orgId = curUser.getColOrgId();
            }

            list = service.queryPositions4select(orgId);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 查询支付方式
     * 
     * @param orgId
     * @return
     */
    @RequiresPermissions(value = { "activity:showActParticipants"}, logical = Logical.OR)
    @RequestMapping(value = "queryPaymentByPaymentId4select")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryPaymentByPaymentId4select(
            @RequestParam(value = "colPaymentType", required = true) Integer colPaymentType) {
        List<PaymentTypeDTO> list = null;

        try {

            list = service.queryPaymentByPaymentType4select(colPaymentType);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 查询索引列表
     * 
     * @return
     */
    @RequiresPermissions(value = { "newsType:query", "actType:queryActTypes" }, logical = Logical.OR)
    @RequestMapping(value = "queryIndexs")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryIndex(@RequestParam(value = "colLabelClass", required = true) String colLabelClass) {
        List<Index> list = null;

        try {
            list = service.queryIndexs(colLabelClass);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 创建索引
     * 
     * @return
     */
    @RequiresPermissions(value = { "newsType:create", "actType:create" }, logical = Logical.OR)
    @RequestMapping(value = "createIndex")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object createIndex(@RequestParam(value = "colLabelClass", required = true) String colLabelClass,
            @RequestParam(value = "colLabelText", required = true) String colLabelText,
            @RequestParam(value = "colDelFlag", required = true) String colDelFlag) {
        ReturnMessage rm = new ReturnMessage();
        int code;

        try {

            code = service.createIndex(colLabelClass, colLabelText, colDelFlag);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 验证机构代码唯一
     * 
     * @return
     */
    @RequestMapping(value = "checkUniqueOrgCode")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object checkUniqueOrgCode(@RequestParam(value = "orgCode", required = true) String orgCode,
            @RequestParam(value = "orgId", required = false) Integer orgId) {
        ReturnMessage rm = new ReturnMessage();
        boolean flag;

        try {

            flag = service.checkUniqueOrgCode(orgCode, orgId);

            rm.setResult(flag);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 验证手机唯一性
     * 
     * @return
     */
    @RequestMapping(value = "checkUniquePhone4AppUer")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object checkUniquePhone4AppUer(@RequestParam(value = "phone", required = true) String phone,
            @RequestParam(value = "appUserId", required = false) Integer appUserId) {
        ReturnMessage rm = new ReturnMessage();
        boolean flag;

        try {

            flag = service.checkUniquePhone4AppUser(phone, appUserId);

            rm.setResult(flag);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 更新索引
     * 
     * @return
     */
    @RequiresPermissions(value = { "newsType:update", "actType:updateActType" }, logical = Logical.OR)
    @RequestMapping(value = "updateIndex")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object udpateIndex(@RequestParam(value = "dictId", required = true) Integer dictId,
            @RequestParam(value = "colLabelClass", required = true) String colLabelClass,
            @RequestParam(value = "colLabelText", required = true) String colLabelText,
            @RequestParam(value = "colDelFlag", required = true) String colDelFlag) {
        ReturnMessage rm = new ReturnMessage();
        int code;

        try {

            code = service.udpateIndex(dictId, colLabelClass, colLabelText, colDelFlag);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 删除索引
     * 
     * @return
     */
    @RequiresPermissions(value = { "newsType:remove", "actType:removeActType" }, logical = Logical.OR)
    @RequestMapping(value = "removeIndex")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object removeIndex(@RequestParam(value = "dictId", required = true) Integer dictId) {
        ReturnMessage rm = new ReturnMessage();
        int code;

        try {

            code = service.removeIndex(dictId);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 查询机构类型
     * 
     * @return
     */
    @RequiresPermissions(value = { "orgType:query" }, logical = Logical.OR)
    @RequestMapping(value = "queryDicts")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryDict(@RequestParam(value = "colDictClass", required = true) String colDictClass) {
        List<Dict> list = null;

        try {
            list = service.queryDicts(colDictClass);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 创建机构类型
     * 
     * @return
     */
    @RequiresPermissions(value = { "orgType:create" }, logical = Logical.OR)
    @RequestMapping(value = "createDict")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object createDict(@RequestParam(value = "colDictClass", required = true) String colDictClass,
            @RequestParam(value = "colDictText", required = true) String colDictText,
            @RequestParam(value = "colDictVal", required = true) String colDictVal) {
        ReturnMessage rm = new ReturnMessage();
        int code;

        try {

            code = service.createDict(colDictClass, colDictText, colDictVal);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 更新机构类型
     * 
     * @return
     */
    @RequiresPermissions(value = { "orgType:update" }, logical = Logical.OR)
    @RequestMapping(value = "updateDict")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object udpateDict(@RequestParam(value = "dictId", required = true) Integer dictId,
            @RequestParam(value = "colDictClass", required = true) String colDictClass,
            @RequestParam(value = "colDictText", required = true) String colDictText,
            @RequestParam(value = "colDictVal", required = true) String colDictVal) {
        ReturnMessage rm = new ReturnMessage();
        int code;

        try {

            code = service.udpateDict(dictId, colDictClass, colDictText, colDictVal);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 删除机构类型
     * 
     * @return
     */
    @RequiresPermissions(value = { "orgType:remove" }, logical = Logical.OR)
    @RequestMapping(value = "removeDict")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object removeDict(@RequestParam(value = "dictId", required = true) Integer dictId) {
        ReturnMessage rm = new ReturnMessage();
        int code;

        try {

            code = service.removeDict(dictId);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 查询前台APP用户
     * 
     * @return
     */
    @RequiresPermissions(value = { "appUser:query" }, logical = Logical.OR)
    @RequestMapping(value = "queryAppUser")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryAppUser(@RequestParam(required = false) String phone) {
        List<AppUser> list = null;

        try {
            list = service.queryAppUser(phone);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 创建前台APP用户
     * 
     * @return
     */
    @RequiresPermissions(value = { "appUser:create" }, logical = Logical.OR)
    @RequestMapping(value = "createAppUser")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object createAppUser(

    @RequestParam(value = "colUserLoginId", required = true) String colUserLoginId,
            @RequestParam(value = "colUserName", required = true) String colUserName,
            @RequestParam(value = "colUserTel", required = true) String colUserTel,
            @RequestParam(value = "colUserMobile", required = true) String colUserMobile,
            @RequestParam(value = "colUserEmail", required = true) String colUserEmail,
            @RequestParam(value = "colUserAddress", required = true) String colUserAddress,
            @RequestParam(value = "colUserJob", required = true) String colUserJob,
            @RequestParam(value = "colUserIndustry", required = true) String colUserIndustry,
            @RequestParam(value = "colUserCompany", required = true) String colUserCompany,
            @RequestParam(value = "colReceiveMsgFlag", required = true) Integer colReceiveMsgFlag,
            @RequestParam(value = "colFriendInvite", required = true) Integer colFriendInvite,
            @RequestParam(value = "userLoginDeviceMac", required = true) String userLoginDeviceMac,
            @RequestParam(value = "colDelFlag", required = true) Integer colDelFlag,
            @RequestParam(value = "colUserOthers", required = true) String colUserOthers) {
        ReturnMessage rm = new ReturnMessage();
        int code;

        try {

            code = service.createAppUser(colUserLoginId, colUserName, colUserTel, colUserMobile, colUserEmail,
                    colUserAddress, colUserJob, colUserIndustry, colUserCompany, colReceiveMsgFlag, colFriendInvite,
                    userLoginDeviceMac, colDelFlag, colUserOthers);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 更新前台APP用户
     * 
     * @return
     */
    @RequiresPermissions(value = { "AppUser:update" }, logical = Logical.OR)
    @RequestMapping(value = "updateAppUser")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object udpateAppUser(@RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "colUserLoginId", required = true) String colUserLoginId,
            @RequestParam(value = "colUserName", required = true) String colUserName,
            @RequestParam(value = "colUserTel", required = true) String colUserTel,
            @RequestParam(value = "colUserMobile", required = true) String colUserMobile,
            @RequestParam(value = "colUserEmail", required = true) String colUserEmail,
            @RequestParam(value = "colUserAddress", required = true) String colUserAddress,
            @RequestParam(value = "colUserJob", required = true) String colUserJob,
            @RequestParam(value = "colUserIndustry", required = true) String colUserIndustry,
            @RequestParam(value = "colUserCompany", required = true) String colUserCompany,
            @RequestParam(value = "colReceiveMsgFlag", required = true) Integer colReceiveMsgFlag,
            @RequestParam(value = "colFriendInvite", required = true) Integer colFriendInvite,
            @RequestParam(value = "userLoginDeviceMac", required = true) String userLoginDeviceMac,
            @RequestParam(value = "colDelFlag", required = true) Integer colDelFlag,
            @RequestParam(value = "colUserOthers", required = true) String colUserOthers) {
        ReturnMessage rm = new ReturnMessage();
        int code;

        try {

            code = service.udpateAppUser(userId, colUserLoginId, colUserName, colUserTel, colUserMobile, colUserEmail,
                    colUserAddress, colUserJob, colUserIndustry, colUserCompany, colReceiveMsgFlag, colFriendInvite,
                    userLoginDeviceMac, colDelFlag, colUserOthers);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 删除前台APP用户
     * 
     * @return
     */
    @RequiresPermissions(value = { "AppUser:remove" }, logical = Logical.OR)
    @RequestMapping(value = "removeAppUser")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object removeAppUser(@RequestParam(value = "userId", required = true) Integer userId) {
        ReturnMessage rm = new ReturnMessage();
        int code;

        try {

            code = service.removeAppUser(userId);

            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 查询前台用户列表
     * 
     * @return
     */
    @RequestMapping(value = "queryAppUsers4auto")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryAppUsers4auto() {
        List<AppUser> list = null;

        try {

            list = service.queryAppUsers4auto();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

}
