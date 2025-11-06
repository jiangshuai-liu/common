package com.sx.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;

/**
 * 描述: 文件工具类
 *
 * @author jiangshuai
 */
public class FileUtils {

    /**
     * 上传文件
     * @param file 文件
     * @param filePath 文件路径
     */
    public static String uploadFile(MultipartFile file,String filePath){
        if(file.isEmpty()){
            return "文件为空";
        }
        try {
            String fileName=file.getOriginalFilename();
            /*上传文件存储位置*/
            String destFileName=filePath+fileName;
            File destFile=new File(destFileName);
            file.transferTo(destFile);
            //文件上传成功显示
        } catch (Exception e) {
            //文件上传失败显示
            return "文件上传失败";
        }
        return null;
    }

    /**
     * 下载文件
     * @param filePath 文件路径
     * @param fileName 文件名
     */
    public static String downloadFile(String filePath,String fileName){
        byte[] data = null;
        // 读取文件字节数组
        try {
            InputStream in = new FileInputStream(filePath+fileName);
            System.out.println("文件大小（字节）="+in.available());
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组进行Base64编码，得到Base64编码的字符串
        Base64.Encoder encoder = Base64.getEncoder();
        return Arrays.toString(encoder.encode(data));
    }

    /*
        //若前端需要流返回文件
            //Base64解码
            byte[] b = Base64.decodeBase64(fileStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成文件
            OutputStream out = new FileOutputStream(fileFilePath);
            out.write(b);
            out.flush();
            out.close();

     */



}
