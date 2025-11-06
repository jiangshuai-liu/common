package com.sx.api.job;


import com.sx.common.helper.StringHelper;
import com.sx.job.entity.JobPO;
import com.sx.job.service.JobService;
import com.sx.job.service.impl.JobServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;


/**
 * 创建定时任务
 * @author jiangshuai
 */
@Slf4j
@Configuration
@EnableScheduling
public class GlobalJobInitializer implements ApplicationRunner {

    @Resource
    private Scheduler scheduler;
    @Resource
    private JobService jobService;
    @Resource
    private JobServiceImpl jobServiceImpl;
    public static void main(String[] args) {

    }

    @Override
    public void run(ApplicationArguments args) throws SchedulerException {
        log.info("初始化自动服务开始");
        List<JobPO> jobList = jobService.getAllJobs();
        if (jobList != null) {
            //清除所有的任务历史记录
            scheduler.clear();
            for (JobPO job : jobList) {
                try {
                    //判断cron表达式是否正确
                    if(!(StringHelper.isNotEmpty(job.getCronExpression())
                            && CronExpression.isValidExpression(job.getCronExpression()))){
                        log.error("jobName：{}——cron表达式错误", job.getName());
                        continue;
                    }
                    jobServiceImpl.scheduleJob(job);
                    log.info("加载【{}】成功", job.getClassName());
                } catch (SchedulerException e) {
                    log.error("jobName：{}——启动失败", job.getName(), e);
                }
            }
        }
        log.info("初始化自动服务结束");
    }
}
