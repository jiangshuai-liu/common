package com.sx.common.entity;

import lombok.Data;

/**
 * @author jiangshuai
 * <p color = "yellow">
 * 文档导出工具类Entity
 * </p>
 */
@Data
public class WorkbookEntity {
    /**
     * sheet页码
     */
    public Integer sheetNum;
    /**
     * 创建行 0行为末尾第一行
     */
    public Integer createRow;
    /**
     * 创建列 0行为第一列
     */
    public Integer createCell;
    /**
     * 字体大小
     */
    public short fontSize;
    /**
     * 添加内容
     */
    public String value;
}
