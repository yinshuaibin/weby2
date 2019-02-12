package com.ferret.utils.staticUtils;

import java.io.*;
import java.nio.charset.Charset;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.*;

@Slf4j
public class FTPUtils {
    //ftp对象
    private FTPClient ftp;
    //需要连接到的ftp端的ip
    private String ip;
    //连接端口，默认21
    private int port;
    //要连接到的ftp端的名字
    private String name;
    //要连接到的ftp端的对应得密码
    private String pwd;

    // 登录ftp服务器
    public void loginFTP(String ip, int port, String name, String pwd) {
        this.ip = ip;
        this.port = port;
        this.name = name;
        this.pwd = pwd;

        ftp = new FTPClient();
        //验证登录
        try {
            ftp.connect(ip, port);
            ftp.login(name, pwd); // 登录服务器
            ftp.setCharset(Charset.forName("UTF-8"));
            ftp.setControlEncoding("UTF-8");
            int replyCode = ftp.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replyCode)){
                log.error("connect failed...ftp服务器:"+this.ip+":"+this.port);
            }
            log.info("connect successfu...ftp服务器:"+this.ip+":"+this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * @Description 向FTP上传文件
     * @param pathname ftp服务保存地址 格式（ftp://192.168.0.111）
     * @param originfilename 待上传文件的名称（绝对地址）
     * @date 2019-01-24 16:25:21
     * @author xieyingchao
     */
    public boolean uploadFile( String pathname,String originfilename){
        boolean flag = false;
        InputStream inputStream = null;
        try{
            log.info("开始上传文件");
            File file = new File(originfilename);
            inputStream = new FileInputStream(file);
//            loginFTP();
            ftp.setFileType(ftp.BINARY_FILE_TYPE);
//            CreateDirecroty(pathname);
            ftp.makeDirectory(pathname);
            ftp.changeWorkingDirectory(pathname);
            ftp.storeFile(file.getName(), inputStream);
            inputStream.close();
            ftp.logout();
            flag = true;
            log.info("上传文件成功");
        }catch (Exception e) {
            log.error("上传文件失败");
            e.printStackTrace();
        }finally{
            if(ftp.isConnected()){
                try{
                    ftp.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    /**
     * @Description 向FTP上传文件
     * @param pathname ftp服务保存地址 格式（ftp://192.168.0.111）
     * @param fileName 上传到ftp的文件名
     * @param originfilename 待上传文件的名称（绝对地址）
     * @date 2019-01-24 16:25:21
     * @author xieyingchao
     */
    public boolean uploadFile( String pathname, String fileName,String originfilename){
        boolean flag = false;
        InputStream inputStream = null;
        try{
            log.info("开始上传文件");
            inputStream = new FileInputStream(new File(originfilename));
//            loginFTP();
            ftp.setFileType(ftp.BINARY_FILE_TYPE);
//            CreateDirecroty(pathname);
            ftp.makeDirectory(pathname);
            ftp.changeWorkingDirectory(pathname);
            ftp.storeFile(fileName, inputStream);
            inputStream.close();
            ftp.logout();
            flag = true;
            log.info("上传文件成功");
        }catch (Exception e) {
            log.error("上传文件失败");
            e.printStackTrace();
        }finally{
            if(ftp.isConnected()){
                try{
                    ftp.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    /**
     * 上传文件
     * @param pathname ftp服务保存地址 格式（ftp://192.168.0.111）
     * @param fileName 上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    public boolean uploadFile( String pathname, String fileName,InputStream inputStream){
        boolean flag = false;
        try{
            log.info("开始上传文件");
//            loginFTP();
            ftp.setFileType(ftp.BINARY_FILE_TYPE);
//            CreateDirecroty(pathname);
            ftp.makeDirectory(pathname);
            ftp.changeWorkingDirectory(pathname);
            ftp.storeFile(fileName, inputStream);
            inputStream.close();
            ftp.logout();
            flag = true;
            log.info("上传文件成功");
        }catch (Exception e) {
            log.error("上传文件失败");
            e.printStackTrace();
        }finally{
            if(ftp.isConnected()){
                try{
                    ftp.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }


    //获取ftp某一文件（路径）下的文件名字,用于查看文件列表
    public void getFilesName(String str) {
        try {
            //获取ftp里面，str里面的文件名字，存入数组中
            FTPFile[] files = ftp.listFiles(str);
            //打印出ftp里面，“Windows”文件夹里面的文件名字
            for (int i = 0; i < files.length; i++) {
                log.info(files[i].getName());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //下载文件
    public void getFile(String file,String str) {
        try {
            //将ftp根目录下的file文件下载到本地目录文件夹下面，并重命名。str:包含路径和新名称
            ftp.retrieveFile("jsoup-1.10.2.jar", new FileOutputStream(new File(str)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //下载文件的第二种方法，优化了传输速度
    public void getFile2(String file,String str) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = ftp.retrieveFileStream(file);

            fos = new FileOutputStream(new File(str));

            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                fos.write(b,0,len);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
    public boolean CreateDirecroty(String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(path)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        System.out.println("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }
    //改变目录路径
    public boolean changeWorkingDirectory(String directory) {
        boolean flag = true;
        try {
            flag = ftp.changeWorkingDirectory(directory);
            if (flag) {
                System.out.println("进入文件夹" + directory + " 成功！");

            } else {
                System.out.println("进入文件夹" + directory + " 失败！开始创建文件夹");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }
    //判断ftp服务器文件是否存在
    public boolean existFile(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftp.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }
    //创建目录
    public boolean makeDirectory(String dir) {
        boolean flag = true;
        try {
            flag = ftp.makeDirectory(dir);
            if (flag) {
                System.out.println("创建文件夹" + dir + " 成功！");

            } else {
                System.out.println("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
