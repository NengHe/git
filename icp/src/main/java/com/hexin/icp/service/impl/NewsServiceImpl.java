package com.hexin.icp.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.Img;
import com.hexin.icp.bean.News;
import com.hexin.icp.dao.ImgDao;
import com.hexin.icp.dao.NewsDao;
import com.hexin.icp.service.NewsService;
import com.hexin.icp.util.FileUtil;

@Service
@Transactional(value = "transactionManager")
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao dao;
    @Autowired
    private ImgDao  imgDao;

    @Override
    public Page<News> queryNews(PageCondition pageCondition, Integer adminUserId, String adminUserType, Integer orgId,
            String colNewsTitle) throws SecurityException, IllegalArgumentException, SQLException,
            InstantiationException, IllegalAccessException, NoSuchFieldException, IOException {

        Page<News> result = null;

        if ("1".equals(adminUserType)) {
            // 机构管理用户只返回本机构发布的新闻
            result = dao.findOrgNewsByOrgId(pageCondition, orgId, colNewsTitle);
        } else if ("2".equals(adminUserType)) {
            // 系统管理用户返回所有的新闻
            result = dao.findAllNews(pageCondition, colNewsTitle);
        }

        return result;
    }

    @Override
    public int createNews(Integer adminUserId, String adminUserType, Integer orgId, String colNewsTitle,
            String colNewsBrief, String colNewsTitleImgUrl, String colNewsIssueTime, String colNewsOrientedType,
            String colNewsSource, String colNewsTypeId, String colSharableFlag, String colNewsContent)
            throws FileNotFoundException, IOException, MyException, NoSuchAlgorithmException, ParseException {

        Integer colCreateBy = adminUserId;
        String colNewsIssuerType = null;
        if ("1".equals(adminUserType)) {
            colNewsIssuerType = "2"; // 机构发布
        } else if ("2".equals(adminUserType)) {
            colNewsIssuerType = "3"; // 系统发布
        }

        // 上传h5内容详情页面
        String cntDetailUrl = FileUtil.uploadNewsH5Page(colNewsTitle, colNewsIssueTime, colNewsSource, colNewsContent);

        // 创建t_news记录
        Integer newsId = dao.creatNews(orgId, colNewsIssuerType, colNewsTitle, colNewsTitleImgUrl, colNewsBrief,
                colNewsIssueTime, colNewsOrientedType, colNewsSource, colNewsTypeId, colSharableFlag, colNewsContent,
                cntDetailUrl, colCreateBy);

        // if (newsTitleImg != null && !newsTitleImg.isEmpty()) {
        // // 上传标题原图压缩图片到服务器
        // Img img = FileUtil.uploadOriginalAndCompress(newsTitleImg);
        // // 创建t_img记录
        // if (img != null) {
        // String originalImgUrl = img.getColImgPath();
        // String compressImgUrl = img.getColImgCompressPath();
        // Integer imgId = imgDao
        // .createImg(originalImgUrl, compressImgUrl);
        // // 创建t_img_rel关联记录（新闻标题图片）
        // String colRelType = "3";
        // int affectedRwos = imgDao.createImgRel(imgId, newsId,
        // colRelType);
        // }
        // }

        return 1;
    }

    @Override
    public int updateNews(Integer adminUserId, String adminUserType, Integer newsId, Integer orgId,
            String colNewsTitle, String colNewsTitleImgUrl, String colNewsBrief, String colNewsIssueTime,
            String colNewsOrientedType, String colNewsSource, String colNewsTypeId, Integer colNewsIssuerId,
            String colSharableFlag, String colNewsContent) throws FileNotFoundException, NoSuchAlgorithmException,
            IOException, MyException, ParseException {

        Integer colLastUpdateBy = adminUserId;
        Img newImg = null;
        Img oldImg = null;
        String oldImgPath = null;
        String oldCompressImgPath = null;
        String newImgPath = null;
        String newCompressImgPath = null;
        Integer newImgId = null;
        int imgAffectedRows;
        int imgRelAffectedRows;
        int newsAffectedRows;

        // 上传h5内容详情页面
        String cntDetailUrl = FileUtil.uploadNewsH5Page(colNewsTitle, colNewsIssueTime, colNewsSource, colNewsContent);

        // 更新t_news记录
        newsAffectedRows = dao.udpateNews(newsId, colNewsTitle, colNewsTitleImgUrl, colNewsBrief, colNewsIssueTime,
                colNewsOrientedType, colNewsSource, colNewsTypeId, colNewsIssuerId, colSharableFlag, colNewsContent,
                cntDetailUrl, colLastUpdateBy);

        // // 上传标题原图压缩图片到服务器
        // if (newsTitleImg != null && !newsTitleImg.isEmpty()) {
        //
        // newImg = FileUtil.uploadOriginalAndCompress(newsTitleImg);
        //
        // if (newImg != null && newsTitleImgId != null) {
        // // 更新t_img记录
        // newImgPath = newImg.getColImgPath();
        // newCompressImgPath = newImg.getColImgCompressPath();
        // imgAffectedRows = imgDao.updateImg(newsTitleImgId, newImgPath, newCompressImgPath);
        //
        // // 删除原始文件
        // oldImg = imgDao.getImgByImgId(newsTitleImgId);
        // oldImgPath = oldImg.getColImgPath();
        // oldCompressImgPath = oldImg.getColImgCompressPath();
        // FileUtil.deleteRemoteFile(oldImgPath, oldCompressImgPath);
        // } else if (newImg != null && newsTitleImgId == null) {
        // // 创建t_img记录
        // newImgPath = newImg.getColImgPath();
        // newCompressImgPath = newImg.getColImgCompressPath();
        // newImgId = imgDao.createImg(newImgPath, newCompressImgPath);
        //
        // // 创建t_img_rel关联记录（新闻标题图片）
        // String colRelType = "3";
        // imgRelAffectedRows = imgDao.createImgRel(newImgId, newsId, colRelType);
        // }
        //
        // }

        return 1;
    }

    @Override
    public int removeNews(Integer adminUserId, Integer newsId) {

        int affetedRows = dao.updateNewsDelFlag(adminUserId, newsId);

        return affetedRows;
    }
}
