package com.hexin.icp.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.csource.common.MyException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hexin.icp.bean.Img;

public class FileUtil {

    protected static Log      logger               = LogFactory.getLog(FileUtil.class);

    private static Properties properties           = PropertyUtil.loadProperties("common.properties");

    private static int        COMPRESS_BACK_WIDTH  = Integer.parseInt(properties.getProperty("upload.compress.img.width"));
    private static int        COMPRESS_BACK_HEIGHT = Integer.parseInt(properties.getProperty("upload.compress.img.height"));
    private static String     H5_TEMPLATE_SUFFIX   = properties.getProperty("upload.h5.template.suffix");

    /**
     * 上传文件
     * 
     * @param file
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws MyException
     * @throws NoSuchAlgorithmException
     */
    public static String uploadFile(MultipartFile file) throws FileNotFoundException, IOException, MyException,
            NoSuchAlgorithmException {
        if (null == file || file.isEmpty()) {
            return null;
        }

        String uploadFileUrl = null;
        byte[] bytes = file.getBytes();
        // 获取MultipartFile的文件后缀名
        String fileExtName = getFileExtName(file);

        // 上传文件
        uploadFileUrl = FastdfsFileManager.uploadFile(bytes, fileExtName);

        return uploadFileUrl;
    }

    /**
     * 上传文件
     * 
     * @param file
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws MyException
     * @throws NoSuchAlgorithmException
     */
    public static Img uploadOriginalAndCompress(MultipartFile file) throws FileNotFoundException, IOException,
            MyException, NoSuchAlgorithmException {
        if (null == file || file.isEmpty()) {
            return null;
        }

        Img img = new Img();
        // 获取MultipartFile的文件后缀名
        String fileExtName = getFileExtName(file);
        if (!"jpg".equalsIgnoreCase(fileExtName) && !"png".equalsIgnoreCase(fileExtName)
                && !"gif".equalsIgnoreCase(fileExtName) && !"jpeg".equals(fileExtName)) {
            logger.error("Illegal image file format:" + fileExtName);
            return null;
        }

        byte[] originaqlBytes = file.getBytes();
        // 压缩图片文件
        byte[] compressBytes = compressBytesForImgMultipartFile(file, fileExtName);
        // 原图文件url
        String uploadOriginalUrl = null;
        // 压缩图文件url
        String uploadCompressedUrl = null;

        // 上传原图文件
        uploadOriginalUrl = FastdfsFileManager.uploadFile(originaqlBytes, fileExtName);
        uploadCompressedUrl = FastdfsFileManager.uploadFile(compressBytes, fileExtName);

        img.setColImgPath(uploadOriginalUrl);
        img.setColImgCompressPath(uploadCompressedUrl);

        return img;
    }

    /**
     * 获取MultipartFile的文件后缀名
     * 
     * @param file
     * @return
     */
    private static String getFileExtName(MultipartFile file) {
        String fileExtName = null;

        String originalFileName = file.getOriginalFilename();
        if (originalFileName.contains(".")) {
            int index = originalFileName.lastIndexOf(".");
            fileExtName = originalFileName.substring(index + 1);
        } else {
            logger.error("Fail to upload file, because the format of filename is illegal.");
        }

        if (fileExtName == null || fileExtName.trim().length() <= 0) {
            // 剪裁图片时上传blob文件没有文件名,这里再根据文件类型定义文件格式名
            fileExtName = getFileExtNameByContentType(file.getContentType());
        }

        return fileExtName;
    }

    /**
     * 根据文件类型获取文件后缀名
     * @param contentType
     * @return
     */
    private static String getFileExtNameByContentType(String contentType) {
        String result = null;
        
        if("image/png".equals(contentType)){
            result = "png";
        }
        
        return result;
    }

    /**
     * 压缩图片文件
     * 
     * @param file
     * @param fileExtName
     * @return
     * @throws IOException
     */
    private static byte[] compressBytesForImgMultipartFile(MultipartFile file, String fileExtName) throws IOException {
        byte[] compressBytes = null;

        BufferedImage srcBi = ImageIO.read(file.getInputStream());

        /** 宽,高设定 */
        BufferedImage bi = new BufferedImage(COMPRESS_BACK_WIDTH, COMPRESS_BACK_HEIGHT, BufferedImage.TYPE_INT_RGB);

        bi.getGraphics().drawImage(
                srcBi.getScaledInstance(COMPRESS_BACK_WIDTH, COMPRESS_BACK_HEIGHT, Image.SCALE_SMOOTH), 0, 0, null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        boolean flag = ImageIO.write(bi, fileExtName, out);
        if (flag) {
            compressBytes = out.toByteArray();
        }

        return compressBytes;
    }

    /**
     * 上传新闻内容详情h5页面
     * 
     * @param colNewsContent
     * @param colNewsSource
     * @param trackerServer
     * @param storageClient
     * @param file
     * @param fileExtName
     * @return
     * @throws IOException
     * @throws MyException
     * @throws NoSuchAlgorithmException
     * @throws ParseException
     */
    public static String uploadNewsH5Page(String title, String issueTime, String source, String content)
            throws IOException, MyException, NoSuchAlgorithmException, ParseException {

        String uploadFileUrl = null;
        byte[] bytes = loadNewsH5TemplateFile(title, issueTime, source, content);

        // 上传文件
        uploadFileUrl = FastdfsFileManager.uploadFile(bytes, H5_TEMPLATE_SUFFIX);

        return uploadFileUrl;
    }

    /**
     * 上传活动内容详情h5页面
     * 
     * @param trackerServer
     * @param storageClient
     * @param file
     * @param fileExtName
     * @return
     * @throws IOException
     * @throws MyException
     * @throws NoSuchAlgorithmException
     */
    public static String uploadActivityH5Page(String startTime, String endTime, String address, String orgnizer,
            String title, String titleImgUrl, String background, String agenda, String guest, String content)
            throws IOException, MyException, NoSuchAlgorithmException {

        String uploadFileUrl = null;
        byte[] bytes = loadActivityH5TemplateFile(startTime, endTime, address, orgnizer, title, titleImgUrl,
                background, agenda, guest, content);

        // 上传文件
        uploadFileUrl = FastdfsFileManager.uploadFile(bytes, H5_TEMPLATE_SUFFIX);

        return uploadFileUrl;
    }

    private static byte[] loadActivityH5TemplateFile(String startTime, String endTime, String address, String orgnizer,
            String title, String titleImgUrl, String background, String agenda, String guest, String content)
            throws IOException {
        String classPath = new File(FileUtil.class.getResource("/").getFile()).getCanonicalPath();
        File file = new File(classPath + "/htmlTemplate/activityTemplate.html");
        Document doc = Jsoup.parse(file, "UTF-8");
        byte[] bytes = null;

        Elements cntElms = doc.select("#content");

        if (cntElms != null && cntElms.size() > 0 && !StringUtils.isEmpty(content)) {
            Element elm = cntElms.first();
            elm.html(content);
        }

        bytes = doc2bytes(doc);

        return bytes;
    }

    //
    // private static byte[] loadActivityH5TemplateFile(String startTime,
    // String endTime, String address, String orgnizer, String title,
    // String titleImgUrl, String background, String agenda, String guest,
    // String content) throws IOException {
    // String classPath = new File(FileUtil.class.getResource("/").getFile())
    // .getCanonicalPath();
    // File file = new File(classPath + "/htmlTemplate/activityTemplate.html");
    // Document doc = Jsoup.parse(file, "UTF-8");
    // byte[] bytes = null;
    //
    // Elements timeElms = doc.select("#time");
    // if (timeElms != null && timeElms.size() > 0
    // && !StringUtils.isEmpty(startTime)
    // && !StringUtils.isEmpty(endTime)) {
    // Element elm = timeElms.first();
    // String time = startTime + " 到  " + endTime;
    // elm.html(time);
    // }
    // Elements addrElms = doc.select("#address");
    // if (addrElms != null && addrElms.size() > 0
    // && !StringUtils.isEmpty(address)) {
    // Element elm = addrElms.first();
    // elm.html(address);
    // }
    // Elements orgnizerElms = doc.select("#orgnizer");
    // if (orgnizerElms != null && orgnizerElms.size() > 0
    // && !StringUtils.isEmpty(orgnizer)) {
    // Element elm = orgnizerElms.first();
    // elm.html(orgnizer);
    // }
    // Elements titleImgElms = doc.select("#titleImg");
    // if (titleImgElms != null && titleImgElms.size() > 0
    // && !StringUtils.isEmpty(titleImgUrl)) {
    // Element elm = titleImgElms.first();
    // elm.attr("src", titleImgUrl);
    // }
    // Elements titleElms = doc.select("#title");
    // if (titleElms != null && titleElms.size() > 0
    // && !StringUtils.isEmpty(title)) {
    // Element elm = titleElms.first();
    // elm.html(title);
    // }
    // Elements backgroundElms = doc.select("#background");
    // if (backgroundElms != null && backgroundElms.size() > 0
    // && !StringUtils.isEmpty(background)) {
    // Element elm = backgroundElms.first();
    // elm.html(background);
    // }
    // Elements agendaElms = doc.select("#agenda");
    // if (agendaElms != null && agendaElms.size() > 0
    // && !StringUtils.isEmpty(agenda)) {
    // Element elm = agendaElms.first();
    // elm.html(agenda);
    // }
    // Elements guestElms = doc.select("#guest");
    // if (guestElms != null && guestElms.size() > 0
    // && !StringUtils.isEmpty(guest)) {
    // Element elm = guestElms.first();
    // elm.html(guest);
    // }
    // Elements cntElms = doc.select("#content");
    // if (cntElms != null && cntElms.size() > 0
    // && !StringUtils.isEmpty(content)) {
    // Element elm = guestElms.first();
    // elm.html(content);
    // }
    //
    // bytes = doc2bytes(doc);
    //
    // return bytes;
    // }

    private static byte[] loadNewsH5TemplateFile(String title, String issueTime, String source, String content)
            throws IOException, ParseException {
        String classPath = new File(FileUtil.class.getResource("/").getFile()).getCanonicalPath();
        File file = new File(classPath + "/htmlTemplate/newsTemplate.html");
        Document doc = Jsoup.parse(file, "UTF-8");
        byte[] bytes = null;

        Elements titleElms = doc.select("#title");
        Elements timeElms = doc.select("#time");
        Elements souerceElms = doc.select("#source");
        Elements cntElms = doc.select("#content");

        if (titleElms != null && titleElms.size() > 0 && !StringUtils.isEmpty(title)) {
            Element elm = titleElms.first();
            elm.html(title);
        }
        if (timeElms != null && timeElms.size() > 0 && !StringUtils.isEmpty(issueTime)) {
            Element elm = timeElms.first();
            String time = getTimeFromIssueTime(issueTime);
            elm.html(time);
        }
        if (souerceElms != null && souerceElms.size() > 0 && !StringUtils.isEmpty(source)) {
            Element elm = souerceElms.first();
            elm.html(source);
        }
        if (cntElms != null && cntElms.size() > 0 && !StringUtils.isEmpty(content)) {
            Element elm = cntElms.first();
            elm.html(content);
        }

        bytes = doc2bytes(doc);

        return bytes;
    }

    private static String getTimeFromIssueTime(String issueTime) throws ParseException {
        String result = "";

        if (StringUtils.isEmpty(issueTime)) {
            return result;
        }

        result = IcpObjectUtil.formatDateStr(issueTime, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");

        return result;
    }

    private static byte[] doc2bytes(Document doc) throws IOException {
        String docStr = doc.html();
        if (StringUtils.isEmpty(docStr)) {
            return null;
        }

        byte[] bytes = docStr.getBytes("utf8");

        return bytes;
    }

    public static void main(String[] args) {

        // try {
        // String url = uploadNewsH5Page("新建的标题", "新内容");
        // System.out.println(url);
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (NoSuchAlgorithmException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (MyException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // try {
        // String classPath = new File(FileUtil.class.getResource("/")
        // .getFile()).getCanonicalPath();
        // String configFilePath = classPath + File.separator
        // + "fdfs_client.conf";
        //
        // File file = new File(configFilePath);
        // if (file.exists()) {
        // byte[] buffer = null;
        // FileInputStream fis = new FileInputStream(file);
        // ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // byte[] b = new byte[1024];
        // int n;
        // while ((n = fis.read(b)) != -1) {
        // bos.write(b, 0, n);
        // }
        // fis.close();
        // bos.close();
        // buffer = bos.toByteArray();
        //
        // // uploadFile(buffer, "fdfs_client.conf", buffer.length);
        //
        //
        // }
        //
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        Properties properties1 = PropertyUtil.loadProperties("common.properties");

    }

    public static void deleteRemoteFile(String... urls) throws IOException, MyException {
        if (urls == null || urls.length < 1) {
            return;
        }

        for (String url : urls) {
            FastdfsFileManager.deleteFile(url);
        }

    }

}
