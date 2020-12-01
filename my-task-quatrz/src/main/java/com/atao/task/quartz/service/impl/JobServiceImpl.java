package com.atao.task.quartz.service.impl;

import com.atao.task.quartz.entity.domain.JobAndTrigger;
import com.atao.task.quartz.entity.form.JobForm;
import com.atao.task.quartz.mapper.JobMapper;
import com.atao.task.quartz.service.JobService;
import com.atao.task.quartz.util.JobUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author atao
 * @date 2020/12/1
 */
@Service
@Slf4j
public class JobServiceImpl implements JobService {

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private Scheduler scheduler;

    /**
     * 添加并启动定时任务
     * @param form 表单参数 {@link JobForm}
     * @throws Exception 异常
     */
    @Override
    public void addJob(JobForm form) throws Exception {
        // 启动调度器
        scheduler.start();

        // 构建job信息
        JobDetail jobDetail = JobBuilder.newJob(JobUtil.getClass(form.getJobClassName()).getClass())
                .withIdentity(form.getJobClassName(), form.getJobGroupName())
                .build();

        // Cron表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(form.getCronExpression());

        // 根据Cron表达式构建一个Trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(form.getJobClassName(), form.getJobGroupName())
                .withSchedule(cronSchedule)
                .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("[定时任务]创建失败!", e);
            throw new Exception("[定时任务]创建失败!");
        }

    }

    /**
     * 删除定时任务
     * @param form 表单参数 {@link JobForm}
     * @throws SchedulerException
     */
    @Override
    public void deleteJob(JobForm form) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(form.getJobClassName(), form.getJobGroupName()));
        scheduler.unscheduleJob(TriggerKey.triggerKey(form.getJobClassName(), form.getJobGroupName()));
        scheduler.deleteJob(JobKey.jobKey(form.getJobClassName(), form.getJobGroupName()));
    }

    /**
     * 暂停定时任务
     * @param form 表单参数 {@link JobForm}
     * @throws SchedulerException
     */
    @Override
    public void pauseJob(JobForm form) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(form.getJobClassName(), form.getJobGroupName()));
    }

    /**
     * 恢复定时任务
     * @param form 表单参数 {@link JobForm}
     * @throws SchedulerException
     */
    @Override
    public void resumeJob(JobForm form) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(form.getJobClassName(), form.getJobGroupName()));
    }

    /**
     * 重新配置定时任务
     * @param form 表单参数 {@link JobForm}
     * @throws Exception
     */
    @Override
    public void cronJob(JobForm form) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(form.getJobClassName(), form.getJobGroupName());

            // 表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(form.getCronExpression());

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 根据Cron表达式构建一个Trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);

        } catch (Exception e) {
            log.error("【定时任务】更新失败！", e);
            throw new Exception("【定时任务】创建失败！");
        }
    }

    /**
     * 查询定时任务列表
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @return 定时任务列表
     */
    @Override
    public PageInfo<JobAndTrigger> list(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<JobAndTrigger> list = jobMapper.list();
        return new PageInfo<>(list);
    }
}
