package com.teletrader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.teletrader.entity")

@SpringBootApplication
public class TeletraderApplication {

	public static void main(String[] args) {

		SpringApplication.run(TeletraderApplication.class, args);
		System.out.println("Aplikacija je startovala");
	}

}
