package net.oschina.xxl.job.util;

import net.oschina.xxl.job.api.entity.CronExpression;

import java.util.Date;

/**
 * @Classname JobUtil
 * @Description
 * @Date 2020/11/18 11:39
 * @Author W.Z
 */
public class JobUtil {

    /**
     * 获取下次有效时间
     * @param cron
     * @param fromTime
     * @return
     * @throws Exception
     */
    public static Date generateNextValidTime(String cron, Date fromTime) throws Exception {
        Date nextValidTime = new CronExpression(cron).getNextValidTimeAfter(fromTime);
        return nextValidTime;
    }
}
