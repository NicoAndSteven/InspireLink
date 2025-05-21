package com.coco.inspirelink;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.coco.inspirelink.mapper")
@SpringBootApplication
public class InspireLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(InspireLinkApplication.class, args);
	}

}
