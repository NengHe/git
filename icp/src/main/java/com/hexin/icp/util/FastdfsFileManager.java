package com.hexin.icp.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.springframework.util.StringUtils;

import com.hexin.icp.bean.FastdfsFileBean;

public class FastdfsFileManager {
	private static Log logger = LogFactory.getLog(FastdfsFileManager.class);

	private static Properties properties = PropertyUtil
			.loadProperties("common.properties");

	private static int CONNECT_TIMOUT_OUT = Integer.parseInt(properties
			.getProperty("fatsdfs.connect.timeout"));
	private static int NETWORK_TIMOUT_OUT = Integer.parseInt(properties
			.getProperty("fatsdfs.network.timeout"));
	public static String CHARSET = properties.getProperty("fatsdfs.charset");
	public static int TRACKER_HTTP_PORT = Integer.parseInt(properties
			.getProperty("fatsdfs.http.tracker.http.port"));
	public static boolean ANTI_STEAL_TOKEN = Boolean.parseBoolean(properties
			.getProperty("fatsdfs.http.anti.steal.token"));
	private static String HTTP_SECREATE_KEY = properties
			.getProperty("fatsdfs.http.secret.key");
	public static String TRACKER_SERVER = properties
			.getProperty("fatsdfs.tracker.server");
	public static int TRACKER_PORT = Integer.parseInt(properties
			.getProperty("fatsdfs.tracker.port"));

	public static boolean IS_NGINX_MAPPING = Boolean.parseBoolean(properties
			.getProperty("fatsdfs.tracker.server.nginx.mapping"));
	private static String NGINX_MAPPING_DOMAIN_NAME = properties
			.getProperty("fatsdfs.tracker.server.mapping.domain.name");
	private static String NGINX_MAPPING_GROUP_NAME = properties
			.getProperty("fatsdfs.tracker.group.name.mapping.name");
	private static String NGINX_ORIGINAL_GROUP_NAME = properties
			.getProperty("fatsdfs.tracker.group.name");

	private static String HTTP_PROTOCOL = "http://";
	private static String URL_SEPARATOR = "/";
	private static String PORT_SEPARATOR = ":";

	private static TrackerClient trackerClient;
	private static TrackerServer trackerServer;
	private static StorageServer storageServer;
	private static StorageClient storageClient;

	static { // Initialize Fast DFS Client configurations

		try {
			// 初始化ClientGlobal,设置相关服务器参数
			initClientGlobal();

			// Tracker服务器列表
			InetSocketAddress[] tracker_servers = new InetSocketAddress[1];
			tracker_servers[0] = new InetSocketAddress(TRACKER_SERVER,
					TRACKER_PORT);
			ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));

			trackerClient = new TrackerClient();
			trackerServer = trackerClient.getConnection();

			storageServer = null;
			storageClient = new StorageClient(trackerServer, storageServer);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

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
	public static String uploadFile(byte[] bytes, String fileExtName)
			throws FileNotFoundException, IOException, MyException,
			NoSuchAlgorithmException {
		if (null == bytes || bytes.length <= 0) {
			return null;
		}

		String uploadFileUrl = null;

		long startTime = System.currentTimeMillis();

		// 上传文件
		String[] results = storageClient.upload_file(bytes, fileExtName, null);

		logger.info("upload_file time used: "
				+ (System.currentTimeMillis() - startTime) + " ms");

		if (results == null) {
			logger.error("upload file fail, error code: "
					+ storageClient.getErrorCode());
			return null;
		}

		// 拼接返回文件url
		uploadFileUrl = getUploadFileUrl(results);

		return uploadFileUrl;
	}

	/**
	 * 拼接返回服务器文件url
	 * 
	 * @param trackerServer
	 * @param storageClient
	 * @param results
	 * @return
	 * @throws IOException
	 * @throws MyException
	 * @throws NoSuchAlgorithmException
	 */
	private static String getUploadFileUrl(String[] results)
			throws IOException, MyException, NoSuchAlgorithmException {
		String uploadFileUrl = null;

		String group_name = results[0];
		String file_id = results[1];
		logger.info(storageClient.get_file_info(group_name, file_id));

		InetSocketAddress inetSockAddr = trackerServer.getInetSocketAddress();
		String hostAddress = inetSockAddr.getAddress().getHostAddress();
		int httpPort = ClientGlobal.g_tracker_http_port;

		if (IS_NGINX_MAPPING) {
			uploadFileUrl = HTTP_PROTOCOL + NGINX_MAPPING_DOMAIN_NAME
					+ URL_SEPARATOR + NGINX_MAPPING_GROUP_NAME + URL_SEPARATOR
					+ file_id;
		} else {
			uploadFileUrl = HTTP_PROTOCOL + hostAddress + PORT_SEPARATOR
					+ httpPort + URL_SEPARATOR + group_name + URL_SEPARATOR
					+ file_id;
		}

		logger.info("file url: " + uploadFileUrl);

		return uploadFileUrl;
	}

	/**
	 * 初始化ClientGlobal,设置相关服务器参数
	 */
	private static void initClientGlobal() {
		// 连接超时的时限，单位为毫秒
		ClientGlobal.setG_connect_timeout(CONNECT_TIMOUT_OUT);
		// 网络超时的时限，单位为毫秒
		ClientGlobal.setG_network_timeout(NETWORK_TIMOUT_OUT);
		ClientGlobal.setG_anti_steal_token(ANTI_STEAL_TOKEN);
		// 字符集
		ClientGlobal.setG_charset(CHARSET);
		ClientGlobal.setG_secret_key(HTTP_SECREATE_KEY);
		// HTTP访问服务的端口号
		ClientGlobal.setG_tracker_http_port(TRACKER_HTTP_PORT);
	}

	public static void deleteFile(String url) throws IOException, MyException {
		FastdfsFileBean bean = getFileBeanFromUrl(url);
		if (bean == null) {
			return;
		}

		String groupName = bean.getGroupName();
		String fileId = bean.getFileId();

		if (IS_NGINX_MAPPING) {
			groupName = NGINX_ORIGINAL_GROUP_NAME;
		}

		try {
			storageClient.delete_file(groupName, fileId);
		} catch (java.lang.NullPointerException e) {
			logger.info("The file:" + groupName + URL_SEPARATOR + fileId
					+ " did not exist in server!");
		} catch (org.csource.common.MyException e1){
			logger.info("The file:" + groupName + URL_SEPARATOR + fileId
					+ " did not exist in current server!");
		}
		logger.info("Ended to delete file:" + url + " fastdfs server");
	}

	private static FastdfsFileBean getFileBeanFromUrl(String url) {
		if (StringUtils.isEmpty(url)) {
			return null;
		}

		FastdfsFileBean bean = new FastdfsFileBean();
		int protocolLength = HTTP_PROTOCOL.length();
		int urlLen = url.length();
		
		if(urlLen > protocolLength){
			String newStr = url.substring(protocolLength);
			String[] arr = newStr.split(URL_SEPARATOR);
			if (arr != null && arr.length >= 4) {
				String groupName = arr[1];
				int gnLen = (arr[0] + groupName).length();
				String fileId = newStr.substring(gnLen + 1);

				bean.setGroupName(groupName);
				bean.setFileId(fileId);
			}
		}

		return bean;
	}

	public static void main(String[] args) {
		FastdfsFileBean bean = getFileBeanFromUrl("http://192.168.27.64:2201/group1/M00/00/00/rBD3gFWV-2zycEnQAACgTSAoOcA204.jpg");
		System.out.println(bean);
	}
}
