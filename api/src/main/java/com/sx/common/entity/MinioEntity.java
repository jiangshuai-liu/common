package com.sx.common.entity;

import lombok.Data;

/**
 * Minio实体类
 * @author jiangshuai
 *
 */
@Data
public class MinioEntity {
    /**
     * 文件服务器名称
     */
    private String fileServerName;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件上传结果
     */
    private boolean result;
    /**
     * 文件上传结果信息
     */
    private String message;
}
