package com.atao.task.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author atao
 * @date 2020/12/01
 **/
@MapperScan(basePackages = {"com.atao.task.quartz.mapper"})
@SpringBootApplication
public class SpringBootQuatrzApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootQuatrzApplication.class, args);
    }

}
