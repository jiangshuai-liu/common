package com.sx.api.job.mapper;

import com.sx.job.entity.JobPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author jiangshuai
 */
@Mapper
@Repository
public interface JobMapper {
    /**
     * <p color=yellow>获取所有任务</p>
     */
    List<JobPO> getAllJobs();
    /**
     * <p color=yellow>根据id获取任务</p>
     */
    JobPO getJobUsId(@Param("po") JobPO po);
    /**
     * <p color=yellow>更新任务</p>
     */
    int updateJob(@Param("po") JobPO po);
}
