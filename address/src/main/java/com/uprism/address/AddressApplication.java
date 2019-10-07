package com.uprism.address;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.uprism.api,com.uprism.address")
public class AddressApplication extends ServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(AddressApplication.class, args);
	}
}
