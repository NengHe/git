package com.hexin.icp.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.News;

public interface NewsDao {

    /**
     * 查询机构发布的新闻
     * 
     * @param pageCondition
     * @param orgId
     * @param colNewsTitile
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws IOException
     */
    public Page<News> findOrgNewsByOrgId(PageCondition pageCondition, Integer orgId, String colNewsTitle)
            throws SecurityException, IllegalArgumentException, SQLException, InstantiationException,
            IllegalAccessException, NoSuchFieldException, IOException;

    /**
     * 查询所有新闻
     * 
     * @param pageCondition
     * @param colNewsTitile
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws IOException
     */
    public Page<News> findAllNews(PageCondition pageCondition, String colNewsTitle) throws SecurityException,
            IllegalArgumentException, SQLException, InstantiationException, IllegalAccessException,
            NoSuchFieldException, IOException;

    /**
     * 创建新闻记录
     * 
     * @return
     */
    public Integer creatNews(Integer orgId, String colNewsIssuerType, String colNewsTitle, String colNewsTitleImgUrl,
            String colNewsBrief, String colNewsIssueTime, String colNewsOrientedType, String colNewsSource,
            String colNewsTypeId, String colSharableFlag, String colNewsContent, String cntDetailUrl,
            Integer colCreateBy);

    /**
     * 更新新闻记录
     * 
     * @param newsId
     * @param colNewsTitle
     * @param colNewsBrief
     * @param colNewsIssueTime
     * @param colNewsSource
     * @param colNewsTypeId
     * @param colSharableFlag
     * @param colNewsContent
     * @param colLastUpdateBy
     * @return
     */
    public int udpateNews(Integer newsId, String colNewsTitle, String colNewsTitleImgUrl, String colNewsBrief,
            String colNewsIssueTime, String colNewsOrientedType, String colNewsSource, String colNewsTypeId,
            Integer colNewsIssuerId, String colSharableFlag, String colNewsContent, String cntDetailUrl,
            Integer colLastUpdateBy);

    /**
     * 软删除新闻记录
     * 
     * @param adminUserId
     * @param newsId
     * @return
     */
    public int updateNewsDelFlag(Integer updateBy, Integer newsId);

}
