package com.qiang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "com.qiang.modules.sys.dao")
@EnableCaching
@EnableAsync // 开启异步任务
@EnableScheduling // 开启定时任务
//@EnableElasticsearchRepositories //
public class AcblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcblogApplication.class, args);
    }

}
