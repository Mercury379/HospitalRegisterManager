package com.hospital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.hospital.mapper")
@SpringBootApplication
public class HospitalManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalManagerApplication.class, args);
    }

}
