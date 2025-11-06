package com.sx.api.job.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


/**
 * job实体类
 * @author jiangshuai
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2031135188315522519L;
    /**
     * job主键
     */
    private Integer id;
    /**
     * job名称
     */
    private String name;
    /**
     * job分组
     */
    private String groupName;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 执行类名，用于反射
     */
    private String className;
    /**
     * 执行方法名
     */
    private String methodName;
    /**
     * job状态 1：可用；0：不可用
     */
    private String status;
}
