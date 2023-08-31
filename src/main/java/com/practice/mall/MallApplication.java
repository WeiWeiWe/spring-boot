package com.practice.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan(basePackages = "com.practice.mall.model.dao")
@EnableSwagger2
public class MallApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallApplication.class, args);
	}

}
