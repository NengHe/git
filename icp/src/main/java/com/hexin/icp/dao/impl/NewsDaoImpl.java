package com.hexin.icp.dao.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.hexin.core.dao.BaseDaoSupport;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.News;
import com.hexin.icp.dao.NewsDao;

@Repository
public class NewsDaoImpl extends BaseDaoSupport implements NewsDao {

    @Override
    public Page<News> findOrgNewsByOrgId(PageCondition pageCondition, Integer orgId, String colNewsTitle)
            throws SecurityException, IllegalArgumentException, SQLException, InstantiationException,
            IllegalAccessException, NoSuchFieldException, IOException {

        String sql = "SELECT n.*,o.col_org_code,o.col_org_name,au.col_admin_username AS colNewsIssuerUsername,al.col_label_text AS colNewsTypeName";
        sql += " FROM t_news n";
        sql += " LEFT JOIN t_admin_user au ON au.admin_user_id=n.col_create_by";
        sql += " LEFT JOIN t_app_lable_index al ON al.col_label_class=1 and al.dict_id=n.col_news_type_id";
        sql += " LEFT JOIN t_org o ON o.org_id=n.col_news_issuer_id";
        sql += " WHERE n.col_del_flag=0 and n.col_news_issuer_type=2 and n.col_news_issuer_id=?";

        if (StringUtils.isNotEmpty(colNewsTitle)) {
            sql += " and n.col_news_title like '%" + colNewsTitle + "%'";
        }

        Page<News> result = super.findPageWithBlob(sql, News.class, pageCondition, orgId);

        return result;
    }

    @Override
    public Page<News> findAllNews(PageCondition pageCondition, String colNewsTitle) throws SecurityException,
            IllegalArgumentException, SQLException, InstantiationException, IllegalAccessException,
            NoSuchFieldException, IOException {

        String sql = "SELECT n.*,o.col_org_code,o.col_org_name,au.col_admin_username AS colNewsIssuerUsername,al.col_label_text AS colNewsTypeName ";
        sql += " FROM t_news n";
        sql += " LEFT JOIN t_admin_user au ON au.admin_user_id=n.col_news_issuer_id";
        sql += " LEFT JOIN t_app_lable_index al ON al.col_label_class=1 and al.dict_id=n.col_news_type_id";
        sql += " LEFT JOIN t_org o ON o.org_id=n.col_news_issuer_id";
        sql += " WHERE n.col_del_flag=0";

        if (StringUtils.isNotEmpty(colNewsTitle)) {
            sql += " and n.col_news_title like '%" + colNewsTitle + "%'";
        }

        Page<News> result = super.findPageWithBlob(sql, News.class, pageCondition);

        return result;
    }

    @Override
    public Integer creatNews(Integer orgId, String colNewsIssuerType, String colNewsTitle, String colNewsTitleImgUrl,
            String colNewsBrief, String colNewsIssueTime, String colNewsOrientedType, String colNewsSource,
            String colNewsTypeId, String colSharableFlag, String colNewsContent, String cntDetailUrl,
            Integer colCreateBy) {

        String sql = "insert into t_news(col_news_oriented_type,col_news_issuer_id,col_news_issuer_type,col_news_title,col_news_title_img_url,"
                + "col_news_brief,col_news_content,col_news_content_detail_url,col_news_type_id,col_news_issue_time,"
                + "col_news_source,col_sharable_flag,col_create_by,col_create_time) "
                + " values(:col_news_oriented_type,:col_news_issuer_id,:col_news_issuer_type,:col_news_title,:col_news_title_img_url,"
                + ":col_news_brief,:col_news_content,:col_news_content_detail_url,:col_news_type_id,:col_news_issue_time,"
                + ":col_news_source,:col_sharable_flag,:col_create_by,now())";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("col_news_oriented_type", colNewsOrientedType);
        params.put("col_news_issuer_id", orgId);
        params.put("col_news_issuer_type", colNewsIssuerType);
        params.put("col_news_title", colNewsTitle);
        params.put("col_news_title_img_url", colNewsTitleImgUrl);
        params.put("col_news_brief", colNewsBrief);
        params.put("col_news_content", colNewsContent);
        params.put("col_news_content_detail_url", cntDetailUrl);
        params.put("col_news_type_id", colNewsTypeId);
        params.put("col_news_issue_time", colNewsIssueTime);
        params.put("col_news_source", colNewsSource);
        params.put("col_sharable_flag", colSharableFlag);
        params.put("col_create_by", colCreateBy);

        Integer newsId = super.insertReturnPK(sql, params);

        return newsId;
    }

    @Override
    public int udpateNews(Integer newsId, String colNewsTitle, String colNewsTitleImgUrl, String colNewsBrief,
            String colNewsIssueTime, String colNewsOrientedType, String colNewsSource, String colNewsTypeId,
            Integer colNewsIssuerId, String colSharableFlag, String colNewsContent, String cntDetailUrl,
            Integer colLastUpdateBy) {

        String sql = "update t_news set col_news_oriented_type=?,col_news_title=?,col_news_title_img_url=?,col_news_brief=?,"
                + "col_news_content=?,col_news_content_detail_url=?,col_news_type_id=?,col_news_issuer_id=?,col_news_issue_time=?,"
                + "col_news_source=?,col_sharable_flag=?,col_last_update_by=?,col_last_update_time=now() where news_id=?";

        int affectedRows = super.update(sql, colNewsOrientedType, colNewsTitle, colNewsTitleImgUrl, colNewsBrief,
                colNewsContent, cntDetailUrl, colNewsTypeId, colNewsIssuerId, colNewsIssueTime, colNewsSource,
                colSharableFlag, colLastUpdateBy, newsId);

        return affectedRows;
    }

    @Override
    public int updateNewsDelFlag(Integer updateBy, Integer newsId) {

        String sql = "update t_news set col_del_flag=1,col_last_update_by=?,col_last_update_time=now() where news_id=?";

        int affectedRows = super.delete(sql, updateBy, newsId);

        return affectedRows;
    }

}
