package com.ms_manage_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MsSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsSecurityApplication.class, args);
	}

}
