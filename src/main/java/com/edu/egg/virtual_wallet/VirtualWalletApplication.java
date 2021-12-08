package com.edu.egg.virtual_wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VirtualWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualWalletApplication.class, args);
	}

}
