package com.qiang.common.utils;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.common.utils
 * @Description: 文件上传工具类
 * @Date: 2019/7/31 0031 19:20
 **/
public class FileUtil {

    //文件上传工具类服务方法

    public static void uploadFile(InputStream file, String filePath) throws Exception{
        FileOutputStream fileOutputStream = null;
        File files = new File(filePath);
        if (files.getParentFile() != null || !files.getParentFile().isDirectory()) {
            //创建父文件夹
            files.getParentFile().mkdirs();
        }
        fileOutputStream = new FileOutputStream(files);
        IOUtils.copy(file, fileOutputStream);
    }
}
