package com.freel00p.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * BlogAdminApplication
 *
 * @author fj
 * @since 2023/8/5 21:40
 */
@SpringBootApplication
@MapperScan("com.freel00p.mapper")
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }
}
