package com.github.jar77.job.util;

import cn.hutool.core.lang.Assert;
import com.github.jar77.job.api.JobInfoApi;
import com.github.jar77.job.api.entity.JobAddFormVO;
import com.github.jar77.job.api.entity.ReturnT;

/**
 * @Classname XxlJobUtil
 * @Description
 * @Date 2020/11/19 10:28
 * @Author W.Z
 */
public class XxlJobUtil {

    public static Long newJob(String jobName,String corn,String executorParam){

        JobAddFormVO vo=new JobAddFormVO();
        vo.setJobDesc(jobName);
        vo.setJobGroup(XxlJobConfigKit.getConfigProperty("xxl-job.group"));
        vo.setCronGenDisplay(corn);
        vo.setScheduleConfCRON(corn);
        vo.setScheduleConf(corn);
        vo.setExecutorRouteStrategy("FIRST");
        vo.setGlueType("BEAN");
        vo.setExecutorHandler(XxlJobConfigKit.getConfigProperty("xxl-job.handler"));
        vo.setExecutorBlockStrategy("SERIAL_EXECUTION");
        vo.setExecutorTimeout("0");
        vo.setExecutorFailRetryCount("0");
        vo.setAuthor(XxlJobConfigKit.getConfigProperty("xxl-job.author"));
        vo.setExecutorParam(executorParam);
        vo.setGlueRemark("GLUE代码初始化");
        vo.setScheduleType("CRON");
        vo.setMisfireStrategy("DO_NOTHING");
        final ReturnT<String> returnT = JobInfoApi.addJob(vo);
        Assert.isTrue(returnT.getCode()==ReturnT.SUCCESS_CODE,returnT.getMsg());
        return Long.valueOf(returnT.getContent());
    }

    public static Long newJobAndStartJob(String jobName,String corn,String executorParam){

        final Long jobId = newJob(jobName, corn, executorParam);
        final ReturnT<String> returnT = JobInfoApi.startJob(jobId);
        Assert.isTrue(returnT.getCode()==ReturnT.SUCCESS_CODE,returnT.getMsg());
        return jobId;
    }

    public static void stop(Long jobId){

        final ReturnT<String> returnT = JobInfoApi.stopJob(jobId);
        Assert.isTrue(returnT.getCode()==ReturnT.SUCCESS_CODE,returnT.getMsg());
    }
}
