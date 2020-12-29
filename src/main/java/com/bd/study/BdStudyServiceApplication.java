package com.bd.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.bd.study"})
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@MapperScan("com.bd.study.repository")
@EnableTransactionManagement    //开启事务注解
// @EnableDiscoveryClient          //开启服务发现(consul)
public class BdStudyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdStudyServiceApplication.class, args);
    }

}
