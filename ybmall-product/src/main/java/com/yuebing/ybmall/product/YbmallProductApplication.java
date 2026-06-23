package com.yuebing.ybmall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//这是一个 Spring Boot 应用的入口,作用：
//1. 开启自动配置
//2. 扫描当前包及子包里的 Spring 组件
//3. 声明这是一个配置类
@SpringBootApplication
public class YbmallProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(YbmallProductApplication.class, args);
    }
}
