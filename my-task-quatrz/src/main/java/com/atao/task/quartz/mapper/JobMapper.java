package com.atao.task.quartz.mapper;

import com.atao.task.quartz.entity.domain.JobAndTrigger;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author atao
 * @date 2020/12/1
 */
@Component
public interface JobMapper {

    /**
     * 查询定时作业和触发器列表
     * @return
     */
    List<JobAndTrigger> list();

}
