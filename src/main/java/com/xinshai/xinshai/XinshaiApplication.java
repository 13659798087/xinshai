package com.xinshai.xinshai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@SpringBootApplication
public class XinshaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(XinshaiApplication.class, args);

	}

}
