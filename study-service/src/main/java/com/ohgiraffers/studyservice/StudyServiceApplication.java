package com.ohgiraffers.studyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// TODO : 추후 user-service를 호출 할 시 사용
//@FeignClient
@EnableDiscoveryClient
public class StudyServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudyServiceApplication.class, args);
	}
}

