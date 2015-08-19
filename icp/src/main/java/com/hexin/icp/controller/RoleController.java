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
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.ResourceTree;
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.bean.Role;
import com.hexin.icp.bean.ZTreeDTO;
import com.hexin.icp.service.RoleService;
import com.hexin.icp.util.WebUtil;

@Controller
@RequestMapping("/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService         service;

    // /**
    // * 查询角色列表
    // *
    // * @see 权限说明：必须拥有permission资源的查看权限
    // */
    // @RequiresPermissions("permission:^[0-1]{4,}1$")
    // @RequestMapping(value = "getRoleTree")
    // @ResponseBody
    // @SuppressWarnings("finally")
    // public List<ResourceTreeDTO> getRoleTree() {
    // List<ZTreeDTO> list = null;
    //
    // try {
    // list = service.getRoleTree();
    // } catch (Exception e) {
    // logger.error(e.getMessage(), e);
    // } finally {
    // return list;
    // }
    // }

    /**
     * 查询角色列表
     * 
     * @see 权限说明：必须拥有permission资源的查看权限
     */
    @RequiresPermissions("role:query")
    @RequestMapping(value = "getRoleTree")
    @ResponseBody
    @SuppressWarnings("finally")
    public List<ZTreeDTO> getRoleTree() {
        List<ZTreeDTO> list = null;

        try {
            list = service.getRoleTree();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 分配资源权限树
     * 
     * @see 权限说明：必须拥有permission资源的分配资源权限
     */
    @RequiresPermissions("role:allocatePermission")
    @RequestMapping(value = "allocatePermission")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object allocatePermission(@RequestParam(value = "roleId", required = true) Integer roleId,
                                     @RequestParam(value = "operationIds", required = true) String operationIds) {
        ReturnMessage rm = new ReturnMessage();
        int code = 0;

        try {

            code = service.allocatePermission(roleId, operationIds);
            rm.setCode(code + "");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 查询资源权限树
     * 
     * @see 权限说明：必须拥有permission资源的分配资源权限
     */
    @RequiresPermissions("role:allocatePermission")
    @RequestMapping(value = "queryPermissionTrees")
    @ResponseBody
    @SuppressWarnings("finally")
    public List<ResourceTree> queryPermissionTrees(@RequestParam(value = "roleId", required = true) Integer roleId) {
        List<ResourceTree> list = null;
        AdminUser curUser = WebUtil.getLoginUser();
        Integer curAdminUserId = curUser.getAdminUserId();

        try {

            list = service.queryPermissionTrees(curAdminUserId, roleId);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 查询角色列表
     * 
     * @see 权限说明：必须拥有permission资源的查看权限
     */
    @RequiresPermissions("role:query")
    @RequestMapping(value = "queryRoles")
    @ResponseBody
    @SuppressWarnings("finally")
    public List<Role> queryRoles(@RequestParam(value = "roleName", required = false) String roleName) {
        List<Role> list = null;

        try {
            list = service.queryRoleList(roleName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return list;
        }
    }

    /**
     * 添加角色
     * 
     * @see权限说明：必须拥有permission资源的查看权限
     */
    @RequiresPermissions("role:create")
    @RequestMapping(value = "insertRole")
    @ResponseBody
    @SuppressWarnings("finally")
    public ReturnMessage insertRole(@RequestParam(value = "parentId", required = false, defaultValue = "0") Integer parentId,
                                    @RequestParam(value = "roleType", required = true) String roleType,
                                    @RequestParam(value = "roleName", required = true) String roleName,
                                    @RequestParam(value = "roleDesc", required = false) String roleDesc) {
        ReturnMessage rm = new ReturnMessage();

        try {
            int affectedRows = service.insertRole(parentId, roleType, roleName, roleDesc);
            rm.setCode(affectedRows + "");
        } catch (Exception e) {
            rm.setCode(Constants.FAILED);
            logger.error(e.getMessage(), e);
        } finally {
            return rm;
        }
    }

    /**
     * 删除角色
     * 
     * @see 权限说明：必须拥有permission资源的删除权限
     */
    @RequiresPermissions("role:remove")
    @RequestMapping(value = "deleteRole")
    @ResponseBody
    @SuppressWarnings("finally")
    public ReturnMessage deleteRole(@RequestParam(value = "roleId", required = true) Integer roleId) {
        ReturnMessage rm = new ReturnMessage();

        try {
            rm = service.deleteRole(roleId);
        } catch (Exception e) {
            rm.setCode(Constants.FAILED);
            logger.error(e.getMessage(), e);
        } finally {
            return rm;
        }
    }

    /**
     * 更新角色
     * 
     * @see 权限说明：必须拥有permission资源的删除权限
     */
    @RequiresPermissions("role:update")
    @RequestMapping(value = "updateRole")
    @ResponseBody
    @SuppressWarnings("finally")
    public ReturnMessage updateRole(@RequestParam(value = "roleId", required = true) Integer roleId,
                                    @RequestParam(value = "parentId", required = false, defaultValue = "0") Integer parentId,
                                    @RequestParam(value = "roleType", required = true) String roleType,
                                    @RequestParam(value = "roleName", required = true) String roleName,
                                    @RequestParam(value = "roleDesc", required = false) String roleDesc) {
        ReturnMessage rm = new ReturnMessage();

        try {
            int affectedRows = service.updateRole(roleId, parentId, roleType, roleName, roleDesc);
            rm.setCode(affectedRows + "");
        } catch (Exception e) {
            rm.setCode(Constants.FAILED);
            logger.error(e.getMessage(), e);
        } finally {
            return rm;
        }
    }
}
