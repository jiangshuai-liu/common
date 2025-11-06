package com.sx.api.job;

import com.sx.common.util.SpringContextUtils;
import com.sx.job.entity.JobPO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.lang.reflect.Method;


/**
 * 并发任务工厂
 * @author jiangshuai
 */
@Setter
@Slf4j
@org.quartz.DisallowConcurrentExecution
public class DisallowConcurrentExecution implements Job {
    private JobPO jobData;

    @Override
    public void execute(JobExecutionContext context) {
        Object object;
        Class<?> clazz;
        try {
            if(null != jobData) {
                log.info("定时任务：{}——执行开始", jobData.getName());
                object = SpringContextUtils.getBean(Class.forName(jobData.getClassName()));
                clazz = object.getClass();
                Method method = clazz.getDeclaredMethod(jobData.getMethodName());
                method.invoke(object);
                log.info("定时任务：{}——执行结束", jobData.getName());
            }
        } catch (Exception e) {
            log.error("定时任务：{}——执行失败", jobData.getName(), e);
        }
    }
}
