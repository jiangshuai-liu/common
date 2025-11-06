package com.sx.common.util;

import com.google.common.base.Charsets;
import com.sx.common.CommonConstant;
import com.sx.common.constants.ContentTypeMapping;
import com.sx.common.entity.MinioEntity;
import com.sx.common.helper.IdGenHelper;
import com.sx.common.helper.StringHelper;
import io.minio.*;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 *  Minio工具类
 *
 * @author jiangshuai
 */
@Slf4j
@Service
public class MinioServiceUtils {

    @Resource
    private MinioClient minioClient;
    /**
     * 目录分隔符
     */
    private static final String SEPARATOR = "/";

    /**
     * 子目录名称
     */
    private String supBucketName;
    /**
     * 存储桶名称
     */
    private String bucketName;

    /**
     * 文件名
     */
    private String fileName;


    /**
     * 上传文件
     *
     * @param bucketName 存储桶名称
     * @param file       文件
     * @return MinioEntity
     */
    public MinioEntity upload(String bucketName, MultipartFile file) {
        MinioEntity minioEntity = new MinioEntity();
        minioEntity.setResult(false);
        String suffix = ContentTypeMapping.getSuffixByFileName(file.getOriginalFilename());
        String fileServerName = IdGenHelper.uuid(false)+suffix;
        this.fileName = fileServerName;
        this.bucketName = bucketName;
        minioEntity.setFileName(file.getOriginalFilename());
        minioEntity.setFileServerName(fileServerName);
        minioEntity.setFilePath(this.bucketName);
        try {
            String msg = upload(bucketName, fileName,file.getInputStream());
            if(StringHelper.isEmpty( msg)){
                minioEntity.setResult(true);
            }else {
                minioEntity.setMessage(msg);
            }
        } catch (IOException e) {
            this.logUploadError(e);
            minioEntity.setMessage(CommonConstant.STRING_ERROR);
        }
        return minioEntity;
    }

    /**
     * 上传文件
     *
     * @param clientBucketName 客户端传过来的的存储桶名称
     * @param file 文件
     * @param fileServerName 文件服务器名称
     * @return String null为成功
     */
    public String upload (String clientBucketName, MultipartFile file, String fileServerName) {
        this.bucketName = clientBucketName;
        this.fileName = fileServerName;
        try {
            return upload(clientBucketName, fileServerName,file.getInputStream());
        } catch (IOException e) {
            this.logUploadError(e);
            return CommonConstant.STRING_ERROR;
        }
    }

    /**
     * 上传文件BASE64串
     *
     * @param bucketName  存储桶名称
     * @param fileName    文件名
     * @param fileContent 文件内容
     * @return String null为成功
     */
    public String uploadFileWithBase64Str(String bucketName, String fileName, String fileContent) {
        InputStream inputStream = new ByteArrayInputStream(Base64.decodeBase64(fileContent));
        return upload(bucketName, fileName,inputStream);
    }

    /**
     * 以文件流上传文件
     * @param bucketName  存储桶名称
     * @param fileName    文件名
     * @param fileData 文件内容
     * @return String null为成功
     */
    public String upload(String bucketName, String fileName,InputStream fileData) {
        this.bucketName = bucketName;
        this.fileName = fileName;
        try {
            if(fileData.available()<=0){
                return "文件大小为空";
            }
            this.getBucSupName();
            //判断存储桶是否存在
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(this.bucketName).build())) {
                //不存在，创建
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(this.bucketName).build());
            }
            //获取文件类型
            String contentType = ContentTypeMapping.getContentType(this.fileName);
            minioClient.putObject(PutObjectArgs.builder()
                    .object(this.supBucketName + SEPARATOR + this.fileName)
                    .bucket(this.bucketName)
                    .contentType(contentType)
                    .stream(fileData, fileData.available(), -1)
                    .build());
            return null;
        } catch (Exception e) {
            this.logUploadError(e);
            return e.getMessage();
        }
    }


    /**
     * 文件下载
     *
     * @param response   响应
     */
    public void download(HttpServletResponse response) {
        //子目录
        String supBucketName = "";
        this.getBucSupName();
        //如果文件不存在，minio会抛出异常
        response.reset();
        // 设置强制下载不打开
        response.setContentType("application/force-download;charset=UTF-8");
        //设置下载文件名
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        try ( InputStream is = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).
                object(supBucketName + SEPARATOR + fileName).build());
         OutputStream os = new BufferedOutputStream(response.getOutputStream())){
            //定义输入输出流
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            response.setHeader("download-result", "1");
        } catch (Exception e) {
            response.setHeader("download-result", "-1");
            this.logUploadError(e);
        }
    }

    /**
     * 下载文件Base64串
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名
     * @return String
     */
    public String downloadFileAsBase64Str(String bucketName, String fileName) {
        this.bucketName = bucketName;
        this.fileName = fileName;
        //子目录
        this.getBucSupName();
        try (InputStream is = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).
                object(supBucketName + SEPARATOR + this.fileName).build())){
            //如果文件不存在，minio会抛出异常
            byte[] bytes = IOUtils.toByteArray(is);
            return new String(Base64.encodeBase64(bytes), Charsets.UTF_8);
        } catch (Exception e) {
            this.logDownloadError(e);
            return CommonConstant.STRING_ERROR;
        }
    }

    /**
     * 获取图片流
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名
     * @param response   响应
     */
    public void showImg(String bucketName, String fileName, HttpServletResponse response) {
        //子目录
        this.bucketName = bucketName;
        this.fileName = fileName;
        this.getBucSupName();
        try (InputStream is = minioClient.getObject(GetObjectArgs.builder().bucket(this.bucketName).
                object(supBucketName + SEPARATOR + this.fileName).build());
             OutputStream os = response.getOutputStream();){
            //如果文件不存在，minio会抛出异常
            BufferedImage img = ImageIO.read(is);
            if (img != null) {
                String suffix = fileName.substring(fileName.indexOf(".") + 1);
                ImageIO.write(img, suffix, os);
            } else {
                log.error("【{}{}{}】目录下的文件【{}】不存在", this.bucketName,SEPARATOR, this.supBucketName, this.fileName);
            }
        } catch (Exception e) {
            this.logDownloadError(e);
        }
    }

    /**
     * 文件删除
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名
     * @return CommonResult<String>
     */
    public boolean delete(String bucketName, String fileName) {
        //子目录
        this.bucketName = bucketName;
        this.fileName = fileName;
        this.getBucSupName();
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(this.bucketName).object(supBucketName + SEPARATOR + this.fileName).build());
            //判断当前存储桶下是否还有别的文件，如果没有，删除存储桶
            Iterator<Result<Item>> iterator = minioClient.listObjects(ListObjectsArgs.builder().bucket(this.bucketName).build()).iterator();
            if (!iterator.hasNext()) {
                //删除存储桶
                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(this.bucketName).build());
            }
            return true;
        } catch (Exception e) {
            log.error("【{}" + SEPARATOR + "{}】目录下的文件【{}】删除失败", this.bucketName, supBucketName, this.fileName);
            return false;
        }
    }

    /**
     * 上传文件失败时，记录错误日志
     *
     * @param e 异常
     */
    private void logUploadError(Exception e) {
        String errorMsg = String.format("文件【%s】上传至存储桶【%s】失败", fileName, bucketName);
        log.error(errorMsg, e);
    }
    /**
     * 下载文件失败时，记录错误日志
     *
     * @param e 异常
     */
    private void logDownloadError(Exception e) {
        String errorMsg = String.format("【%s%s%s】目录下的文件【%s】下载失败", bucketName, SEPARATOR, supBucketName, fileName);
        log.error(errorMsg, e);
    }

    /**
     * 获取bucketName和supBucketName
     */
    private void getBucSupName(){
        if (bucketName.contains(SEPARATOR)) {
            //如果是多级目录，将第一层目录作为bucketName，后面的目录拼接到文件名上，minio只支持这种方式去上传多级目录结构的文件
            //如果是/test/test2
            if (bucketName.startsWith(SEPARATOR)) {
                //bucketName = test
                bucketName = bucketName.substring(1);
            }
            supBucketName = bucketName.substring(bucketName.indexOf(SEPARATOR) + 1);
            if (supBucketName.endsWith(SEPARATOR)) {
                supBucketName = supBucketName.substring(0, supBucketName.length() - 1);
            }
            bucketName = bucketName.substring(0, bucketName.indexOf(SEPARATOR));
        }
    }
}
