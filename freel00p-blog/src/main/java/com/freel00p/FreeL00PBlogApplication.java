package com.freel00p;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * FreeL00PBlogApplication
 *
 * @author fj
 * @since 2023/7/1 21:23
 */
@SpringBootApplication
@MapperScan("com.freel00p.mapper")
@EnableScheduling //开启定时任务功能
public class FreeL00PBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(FreeL00PBlogApplication.class,args);
    }
}
