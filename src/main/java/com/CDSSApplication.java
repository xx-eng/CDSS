package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.mapper")
public class CDSSApplication {

    public static void main(String[] args) {
        SpringApplication.run(CDSSApplication.class, args);
    }

}
