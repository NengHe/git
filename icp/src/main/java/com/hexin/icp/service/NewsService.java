package com.hexin.icp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.csource.common.MyException;
import org.springframework.web.multipart.MultipartFile;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.News;

public interface NewsService {

    /**
     * 查询新闻列表
     * 
     * @param pageCondition
     * @param adminUserId
     * @param adminUserType
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
    public Page<News> queryNews(PageCondition pageCondition, Integer adminUserId, String adminUserType, Integer orgId,
            String colNewsTitle) throws SecurityException, IllegalArgumentException, SQLException,
            InstantiationException, IllegalAccessException, NoSuchFieldException, IOException;

    /**
     * 创建新闻
     * 
     * @param adminUserId
     * @param adminUserType
     * @param orgId
     * @param colNewsTitle
     * @param colNewsBrief
     * @param newsTitleImg
     * @param colNewsIssueTime
     * @param colNewsSource
     * @param colNewsTypeId
     * @param colSharableFlag
     * @param colNewsContent
     * @return
     * @throws MyException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws ParseException
     */
    public int createNews(Integer adminUserId, String adminUserType, Integer orgId, String colNewsTitle,
            String colNewsBrief, String colNewsTitleImgUrl, String colNewsIssueTime, String colNewsOrientedType,
            String colNewsSource, String colNewsTypeId, String colSharableFlag, String colNewsContent)
            throws FileNotFoundException, IOException, MyException, NoSuchAlgorithmException, ParseException;

    /**
     * 更新图片记录
     * 
     * @param adminUserId
     * @param adminUserType
     * @param newsId
     * @param orgId
     * @param colNewsTitle
     * @param colNewsBrief
     * @param newsTitleImgId
     * @param newsTitleImg
     * @param colNewsIssueTime
     * @param colNewsSource
     * @param colNewsTypeId
     * @param colSharableFlag
     * @param colNewsContent
     * @return
     * @throws MyException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws FileNotFoundException
     * @throws ParseException
     */
    public int updateNews(Integer adminUserId, String adminUserType, Integer newsId, Integer orgId,
            String colNewsTitle, String colNewsTitleImgUrl, String colNewsBrief, String colNewsIssueTime,
            String colNewsOrientedType, String colNewsSource, String colNewsTypeId, Integer colNewsIssuerId,
            String colSharableFlag, String colNewsContent) throws FileNotFoundException, NoSuchAlgorithmException,
            IOException, MyException, ParseException;

    /**
     * 删除新闻
     * 
     * @param adminUserId
     * @param newsId
     * @return
     */
    public int removeNews(Integer adminUserId, Integer newsId);

}
