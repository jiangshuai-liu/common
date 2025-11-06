package com.sx.api.job.service.impl;


import com.sx.common.CommonConstant;
import com.sx.job.DisallowConcurrentExecution;
import com.sx.job.entity.JobPO;
import com.sx.job.mapper.JobMapper;
import com.sx.job.service.JobService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 定时任务服务实现类
 * @author jiangshuai
 */
@Service
@Slf4j
public class JobServiceImpl implements JobService {
    @Resource
    private JobMapper mapper;
    @Resource
    private Scheduler scheduler;
    /**
     * <p color=yellow>获取所有定时任务</p>
     */
    @Override
    public List<JobPO> getAllJobs() {
        return mapper.getAllJobs();
    }
    /**
     * <p color=yellow>暂停定时任务</p>
     */
    @Override
    public String pauseJob(Integer id) {
        JobPO po = new JobPO();
        po.setStatus(CommonConstant.STRING_1);
        po.setId(id);
        JobPO jobPo = mapper.getJobUsId(po);
        try {
            po.setStatus(CommonConstant.STRING_0);
            if(null != jobPo) {
                if(scheduler.checkExists(JobKey.jobKey(jobPo.getClassName(), jobPo.getGroupName()))) {
                    int ret = mapper.updateJob(po);
                    if(ret > 0) {
                        scheduler.pauseJob(JobKey.jobKey(jobPo.getClassName(), jobPo.getGroupName()));
                    }
                }else{
                    return "任务id:"+id+"【"+jobPo.getName()+"】未加载";
                }
            }else{
                return "无效任务id：" + id;
            }
        }catch(Exception e) {
            log.error("暂停执行任务【id:{}】异常：{}", id, e.getMessage(), e);
            return e.getMessage();
        }
        return "任务id:"+id+"【"+jobPo.getName()+"】已暂停执行";
    }

    /**
     * <p color=yellow>恢复定时任务</p>
     */
    @Override
    public String resumeJob(Integer id) {
        JobPO po = new JobPO();
        po.setId(id);
        po.setStatus(CommonConstant.STRING_0);
        JobPO jobPo = mapper.getJobUsId(po);
        try {
            po.setStatus(CommonConstant.STRING_1);
            if(null != jobPo) {
                if(scheduler.checkExists(JobKey.jobKey(jobPo.getClassName(), jobPo.getGroupName()))) {
                    int ret = mapper.updateJob(po);
                    if(ret > 0) {
                        scheduler.resumeJob(JobKey.jobKey(jobPo.getClassName(), jobPo.getGroupName()));
                    }
                }else{
                    return "任务id:"+id+"【"+jobPo.getName()+"】未加载";
                }
            }else{
                return "无效任务id：" + id;
            }
        }catch(Exception e) {
            log.error("恢复执行任务【id:{}】异常：{}", id, e.getMessage(), e);
            return e.getMessage();
        }
        return "任务id:"+id+"【"+jobPo.getName()+"】已恢复执行";
    }
    /**
     * <p color=yellow>重新加载定时任务</p>
     */
    @Override
    public String reloadJob(Integer id) {
        try {
            JobPO po = new JobPO();
            po.setStatus(CommonConstant.STRING_1);
            po.setId(id);
            int ret = mapper.updateJob(po);
            if(ret > 0) {
                JobPO jobPo = mapper.getJobUsId(po);
                if(scheduler.checkExists(JobKey.jobKey(jobPo.getClassName(), jobPo.getGroupName()))) {
                    // 已经加载到job中直接恢复执行
                    scheduler.resumeJob(JobKey.jobKey(jobPo.getClassName(), jobPo.getGroupName()));
                }else {
                   this.scheduleJob(jobPo);
                }
                return "重新加载任务【" + jobPo.getName() + "】成功";
            }else{
                return "无效任务id：" + id;
            }
        }catch(Exception e) {
            log.error("重新加载任务【id:{}】异常：{}", id, e.getMessage(), e);
            return e.getMessage();
        }
    }

    /**
     * <p color=yellow>创建定时任务</p>
     * @param po 定时任务信息
     */
    public void scheduleJob(JobPO po) throws SchedulerException {
        //创建调度器
        TriggerKey triggerKey = TriggerKey.triggerKey(po.getClassName(), po.getGroupName());
        //创建Cron调度器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(po.getCronExpression());
        //创建Cron触发器
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
        //创建线程
        Class<? extends Job> clazz =  DisallowConcurrentExecution.class;
        //创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(po.getClassName(), po.getGroupName()).build();
        //将job添加到Map集合中，方便在Factory中获取相应的job信息
        jobDetail.getJobDataMap().put("jobData", po);
        //执行Job
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
