package com.hexin.icp;  
  
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.UUID;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class DFSTest2 {  
        public String local_filename = "D:\\seafood_project\\fast_DFS\\src\\client.conf";  
        public String conf_filename = "D:\\seafood_project\\fast_DFS\\src\\client.conf";  
        public String jpg = "F:\\iptest.jpg";
        @Test  
        public void testUpload() {  
  
                try {  
                        // 连接超时的时限，单位为毫秒  
                        ClientGlobal.setG_connect_timeout(2000);  
                        // 网络超时的时限，单位为毫秒  
                        ClientGlobal.setG_network_timeout(30000);  
                        ClientGlobal.setG_anti_steal_token(false);  
                        // 字符集  
                        ClientGlobal.setG_charset("UTF-8");  
                        ClientGlobal.setG_secret_key("FastDFS1234567890");  
                        // HTTP访问服务的端口号  
                        ClientGlobal.setG_tracker_http_port(8081);  
                        // Tracker服务器列表  
                        InetSocketAddress[] tracker_servers = new InetSocketAddress[1];  
                        tracker_servers[0] = new InetSocketAddress("172.16.247.128", 22122);  
                        ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));  
  
                        TrackerClient tracker = new TrackerClient();  
                        TrackerServer trackerServer = tracker.getConnection();  
                        StorageServer storageServer = null;  
  
                        StorageClient storageClient = new StorageClient(trackerServer, storageServer);  
                        NameValuePair nvp[] = new NameValuePair[] { new NameValuePair("age", "18"),  
                                        new NameValuePair("sex", "male") };  
                        String fileIds[] = storageClient.upload_file(jpg, null, nvp);  
  
                        System.out.println(fileIds.length);  
                        System.out.println("组名：" + fileIds[0]);  
                          
                        System.out.println("路径: " + fileIds[1]);  
  
                } catch (FileNotFoundException e) {  
                        e.printStackTrace();  
                } catch (IOException e) {  
                        e.printStackTrace();  
                } catch (MyException e) {  
                        e.printStackTrace();  
                }  
        }  
  
        @Test  
        public void testDownload() {  
                try {  
                        ClientGlobal.init(conf_filename);  
                        TrackerClient tracker = new TrackerClient();  
                        TrackerServer trackerServer = tracker.getConnection();  
                        StorageServer storageServer = null;  
  
                        StorageClient storageClient = new StorageClient(trackerServer, storageServer);  
                        byte[] b = storageClient.download_file("g1", "M00/A6/97/wKjrh1Nn0Y6AAei8AAACq0FJbOo69.conf");  
                        System.out.println(b);  
                        getFile(b, "d:\\", UUID.randomUUID().toString() + ".conf");  
                } catch (Exception e) {  
                        e.printStackTrace();  
                }  
        }  
  
        private void getFile(byte[] b, String string, String string2) {  
  
        }  
  
        @Test  
        public void testGetFileInfo() {  
                try {  
                        ClientGlobal.init(conf_filename);  
  
                        TrackerClient tracker = new TrackerClient();  
                        TrackerServer trackerServer = tracker.getConnection();  
                        StorageServer storageServer = null;  
  
                        StorageClient storageClient = new StorageClient(trackerServer, storageServer);  
                        FileInfo fi = storageClient.get_file_info("group1",  
                                        "M00/00/00/wKjrhlNQR5aAGb0lAAACquP_VhU29.conf");  
                        System.out.println(fi.getSourceIpAddr());  
                        System.out.println(fi.getFileSize());  
                        System.out.println(fi.getCreateTimestamp());  
                        System.out.println(fi.getCrc32());  
                } catch (Exception e) {  
                        e.printStackTrace();  
                }  
        }  
  
        @org.junit.Test  
        public void testGetFileMate() {  
                try {  
                        ClientGlobal.init(conf_filename);  
  
                        TrackerClient tracker = new TrackerClient();  
                        TrackerServer trackerServer = tracker.getConnection();  
                        StorageServer storageServer = null;  
  
                        StorageClient storageClient = new StorageClient(trackerServer, storageServer);  
                        NameValuePair nvps[] = storageClient.get_metadata("group1",  
                                        "M00/00/00/wKgUoFGwRMmASXSNAAAA4y5hK3c11.conf");  
                        for (NameValuePair nvp : nvps) {  
                                System.out.println(nvp.getName() + ":" + nvp.getValue());  
                        }  
                } catch (Exception e) {  
                        e.printStackTrace();  
                }  
        }  
  
        @org.junit.Test  
        public void testDelete() {  
                try {  
                        ClientGlobal.init(conf_filename);  
  
                        TrackerClient tracker = new TrackerClient();  
                        TrackerServer trackerServer = tracker.getConnection();  
                        StorageServer storageServer = null;  
  
                        StorageClient storageClient = new StorageClient(trackerServer, storageServer);  
                        int i = storageClient.delete_file("group1", "M00/00/00/wKgUoFGwRMmASXSNAAAA4y5hK3c11.conf");  
                        System.out.println(i == 0 ? "删除成功" : "删除失败:" + i);  
                } catch (Exception e) {  
                        e.printStackTrace();  
                }  
        }  
} 