package com.vegiecrud.vegie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VegieApplication {

	public static void main(String[] args) {
		SpringApplication.run(VegieApplication.class, args);
	}

}
