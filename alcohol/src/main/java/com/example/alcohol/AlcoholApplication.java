package com.example.alcohol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AlcoholApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlcoholApplication.class, args);
	}

}
