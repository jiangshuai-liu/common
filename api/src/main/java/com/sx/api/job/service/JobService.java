package com.sx.api.job.service;

import com.sx.job.entity.JobPO;

import java.util.List;

/**
 * 定时任务服务
 * @author jiangshuai
 */
public interface JobService {
    /**
     * 获取所有定时任务
     */
    List<JobPO> getAllJobs();
    /**
     * 暂停定时任务
     */
    String pauseJob(Integer id);
    /**
     * 恢复定时任务
     */
    String resumeJob(Integer id);
    /**
     * 重新加载定时任务
     */
    String reloadJob(Integer id);
}
