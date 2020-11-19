package net.oschina.xxl.job.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import net.oschina.xxl.job.api.entity.*;
import net.oschina.xxl.job.constant.*;
import net.oschina.xxl.job.util.HttpKit;
import net.oschina.xxl.job.util.JobUtil;
import net.oschina.xxl.job.util.XxlJobConfigKit;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Classname JobApi
 * @Description
 * @Date 2020/11/17 17:20
 * @Author W.Z
 */
public class JobInfoApi {

    private static Logger logger = Logger.getLogger(JobInfoApi.class.getName());
    /**
     * 添加job
     * @param form
     * @return
     */
    public static ReturnT<String> addJob(JobAddFormVO form) {

        final String url = XxlJobConfigKit.getConfigProperty(UrlConstant.BASE_URL_KEY) + UrlConstant.URL_JOB_ADD;
        final Map<String, Object> paramMap = BeanUtil.beanToMap(form);
        return HttpKit.postFormObj(url, paramMap, ReturnT.class);
    }

    /**
     * 更新job
     * @param form
     * @return
     */
    public static ReturnT<String> updateJob(JobUpdateFormVO form) {

        final String url = XxlJobConfigKit.getConfigProperty(UrlConstant.BASE_URL_KEY) + UrlConstant.URL_JOB_UPDATE;
        final Map<String, Object> paramMap = BeanUtil.beanToMap(form);
        return HttpKit.postFormObj(url, paramMap, ReturnT.class);
    }

    /**
     * 启动job
     * @param id 任务id
     * @return
     */
    public static ReturnT<String> startJob(Long id){

        final String url = XxlJobConfigKit.getConfigProperty(UrlConstant.BASE_URL_KEY) + UrlConstant.URL_JOB_START;
        final Map<String, Object> paramMap = MapUtil.of("id",id);
        return HttpKit.postFormObj(url, paramMap, ReturnT.class);
    }

    /**
     * 停止job
     * @param id 任务id
     * @return
     */
    public static ReturnT<String> stopJob(Long id){

        final String url = XxlJobConfigKit.getConfigProperty(UrlConstant.BASE_URL_KEY) + UrlConstant.URL_JOB_STOP;
        final Map<String, Object> paramMap = MapUtil.of("id",id);
        return HttpKit.postFormObj(url, paramMap, ReturnT.class);
    }

    /**
     * 触发任务,直接执行
     * @param id 任务id
     * @param executorParam 执行器参数,可为null
     * @param addressList 执行地址,可为null
     * @return
     */
    public static ReturnT<String> triggerJob(Long id,String executorParam,String addressList){

        final String url = XxlJobConfigKit.getConfigProperty(UrlConstant.BASE_URL_KEY) + UrlConstant.URL_JOB_TRIGGER;
        final Map<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("id",id);
        paramMap.put("executorParam",executorParam);
        paramMap.put("addressList",addressList);
        return HttpKit.postFormObj(url, paramMap, ReturnT.class);
    }

    /**
     * 删除job
     * @param id 任务id
     * @return
     */
    public static ReturnT<String> removeJob(Long id){

        final String url = XxlJobConfigKit.getConfigProperty(UrlConstant.BASE_URL_KEY) + UrlConstant.URL_JOB_REMOVE;
        final Map<String, Object> paramMap = MapUtil.of("id",id);
        return HttpKit.postFormObj(url, paramMap, ReturnT.class);
    }

    /**
     * 获取下次触发时间
     * @param scheduleConf cron表达式
     * @return
     */
    public static ReturnT<List<String>> nextTriggerTime(String scheduleConf) {

        List<String> result = new ArrayList<>();
        try {
            Date lastTime = new Date();
            for (int i = 0; i < 5; i++) {
                lastTime = JobUtil.generateNextValidTime(scheduleConf, lastTime);
                if (lastTime != null) {
                    result.add(DateUtil.formatDateTime(lastTime));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING,e.getMessage(),e);
            return new ReturnT<>(ReturnT.FAIL_CODE,  e.getMessage());
        }
        return new ReturnT<>(result);
    }
}
