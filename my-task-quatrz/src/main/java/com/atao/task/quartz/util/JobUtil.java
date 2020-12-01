package com.atao.task.quartz.util;

import com.atao.task.quartz.job.base.BaseJob;

/**
 * 定时任务反射工具类
 * @author atao
 * @date 2020/12/1
 */
public class JobUtil {

    /**
     * 根据全类名获取Job实例
     * @param className Job全类名
     * @return {@link BaseJob} 实例
     * @throws Exception 异常
     */
    public static BaseJob getClass(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        return (BaseJob) clazz.newInstance();
    }

}
