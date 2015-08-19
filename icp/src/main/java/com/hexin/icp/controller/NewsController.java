package com.hexin.icp.controller;

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
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.News;
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.service.NewsService;
import com.hexin.icp.util.WebUtil;

@Controller
@RequestMapping("/news")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService         service;

    /**
     * 添加新闻
     * 
     * @return
     */
    @RequiresPermissions("news:create")
    @RequestMapping(value = "createNews")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object createNews(@RequestParam(value = "colNewsTitle", required = true) String colNewsTitle,
            @RequestParam(value = "colNewsBrief", required = false) String colNewsBrief,
            @RequestParam(value = "colNewsTitleImgUrl", required = false) String colNewsTitleImgUrl,
            @RequestParam(value = "colNewsIssueTime", required = false) String colNewsIssueTime,
            @RequestParam(value = "colNewsOrientedType", required = true) String colNewsOrientedType,
            @RequestParam(value = "colNewsIssuerId", required = false) Integer colNewsIssuerId,
            @RequestParam(value = "colNewsSource", required = false) String colNewsSource,
            @RequestParam(value = "colNewsTypeId", required = false) String colNewsTypeId,
            @RequestParam(value = "colSharableFlag", required = false) String colSharableFlag,
            @RequestParam(value = "colNewsContent", required = true) String colNewsContent) {

        ReturnMessage rm = new ReturnMessage();

        try {
            AdminUser curUser = WebUtil.getLoginUser();
            Integer adminUserId = curUser.getAdminUserId();
            String adminUserType = curUser.getColAdminUserType();

            int code = service.createNews(adminUserId, adminUserType, colNewsIssuerId, colNewsTitle, colNewsBrief,
                    colNewsTitleImgUrl, colNewsIssueTime, colNewsOrientedType, colNewsSource, colNewsTypeId,
                    colSharableFlag, colNewsContent);
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
    @RequiresPermissions("news:remove")
    @RequestMapping(value = "removeNews")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object removeNews(@RequestParam(value = "newsId", required = true) Integer newsId) {

        ReturnMessage rm = new ReturnMessage();

        try {
            AdminUser curUser = WebUtil.getLoginUser();
            Integer adminUserId = curUser.getAdminUserId();

            int code = service.removeNews(adminUserId, newsId);
            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 添加新闻
     * 
     * @return
     */
    @RequiresPermissions("news:update")
    @RequestMapping(value = "updateNews")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object updateNews(@RequestParam(value = "newsId", required = true) Integer newsId,
            @RequestParam(value = "colNewsTitle", required = true) String colNewsTitle,
            @RequestParam(value = "colNewsBrief", required = false) String colNewsBrief,
            @RequestParam(value = "colNewsTitleImgUrl", required = false) String colNewsTitleImgUrl,
            @RequestParam(value = "colNewsIssueTime", required = false) String colNewsIssueTime,
            @RequestParam(value = "colNewsOrientedType", required = true) String colNewsOrientedType,
            @RequestParam(value = "colNewsSource", required = false) String colNewsSource,
            @RequestParam(value = "colNewsTypeId", required = false) String colNewsTypeId,
            @RequestParam(value = "colNewsIssuerId", required = false) Integer colNewsIssuerId,
            @RequestParam(value = "colSharableFlag", required = false) String colSharableFlag,
            @RequestParam(value = "colNewsContent", required = true) String colNewsContent) {

        ReturnMessage rm = new ReturnMessage();

        try {
            AdminUser curUser = WebUtil.getLoginUser();
            Integer adminUserId = curUser.getAdminUserId();
            String adminUserType = curUser.getColAdminUserType();
            Integer orgId = curUser.getColOrgId();

            int code = service.updateNews(adminUserId, adminUserType, newsId, orgId, colNewsTitle, colNewsTitleImgUrl,
                    colNewsBrief, colNewsIssueTime, colNewsOrientedType, colNewsSource, colNewsTypeId, colNewsIssuerId,
                    colSharableFlag, colNewsContent);
            rm.setCode(code + "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setCode(Constants.FAILED);
        } finally {
            return rm;
        }
    }

    /**
     * 查询新闻列表
     * 
     * @return
     */
    @RequiresPermissions("news:query")
    @RequestMapping(value = "queryNews")
    @ResponseBody
    @SuppressWarnings("finally")
    public Object queryNews(PageCondition pageCondition,
            @RequestParam(value = "colNewsTitle", required = false) String colNewsTitle) {

        Page<News> result = null;

        try {
            AdminUser curUser = WebUtil.getLoginUser();
            Integer adminUserId = curUser.getAdminUserId();
            String adminUserType = curUser.getColAdminUserType();
            Integer orgId = curUser.getColOrgId();

            result = service.queryNews(pageCondition, adminUserId, adminUserType, orgId, colNewsTitle);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return result;
        }
    }

}
