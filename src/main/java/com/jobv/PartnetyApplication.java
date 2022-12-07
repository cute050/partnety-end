package com.jobv;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jobv.mapper")
public class PartnetyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartnetyApplication.class, args);
    }

}
