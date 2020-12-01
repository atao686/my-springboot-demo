package com.atao.task.quartz.job.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Job 基类，主要是在 {@link org.quartz.Job} 上再封装一层，只让我们自己项目里的Job去实现
 * @author atao
 * @date 2020/12/1
 */
public interface BaseJob extends Job {

    @Override
    void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException;
}
