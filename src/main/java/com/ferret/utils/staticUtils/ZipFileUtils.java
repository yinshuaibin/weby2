package com.ferret.utils.staticUtils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
/**
 * Created by wzj on 2016/9/9.
 */
@Slf4j
public class ZipFileUtils
{
  /**
   * 解压到指定目录
   */
  public static void unZipFiles(String zipPath,String descDir)throws IOException
  {
    unZipFiles(new File(zipPath), descDir);
  }
  /**
   * 解压文件到指定目录
   */
  @SuppressWarnings("rawtypes")
  public static void unZipFiles(File zipFile,String descDir)throws IOException {
    File pathFile = new File(descDir);
    if(!pathFile.exists()) {
      boolean mkdirs = pathFile.mkdirs();
      if(!mkdirs){
        log.error("名称为:"+pathFile.getName()+" 的文件夹创建失败,请检查文件路径");
        return;
      }
    }
    //解决zip文件中有中文目录或者中文文件
    InputStream in = null;
    ZipFile zip = null;
    OutputStream out = null;
    try {
      zip = new ZipFile(zipFile, Charset.forName("GBK"));
      for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
        ZipEntry entry = (ZipEntry) entries.nextElement();
        String zipEntryName = entry.getName();
        in = zip.getInputStream(entry);
        String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
        System.err.println("outPath------" + outPath);
        System.err.println("outPath.lastIndexOf('/')------" + outPath.lastIndexOf('/'));
        //判断路径是否存在,不存在则创建文件路径
        File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
        if (!file.exists()) {//如果文件夹不存在
          boolean mkdirs = file.mkdirs();
          if (!mkdirs) {
            log.error("名称为:" + file.getName() + " 的文件夹创建失败,请检查文件路径");
            return;
          }
        }
        //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
        if (new File(outPath).isDirectory()) {
          continue;
        }
        //输出文件路径信息
        System.out.println(outPath);
        out = new FileOutputStream(outPath);
        byte[] buf1 = new byte[1024];
        int len;
        while ((len = in.read(buf1)) > 0) {
          out.write(buf1, 0, len);
        }
      }
      zip.close();
      System.out.println("******************解压完毕********************");
    }catch (IOException e){
      e.printStackTrace();
    }finally {
      if(null != zip){
        zip.close();
      }
      if(null != in){
        in.close();
      }
      if(null != out){
        out.close();
      }
    }
  }
}
