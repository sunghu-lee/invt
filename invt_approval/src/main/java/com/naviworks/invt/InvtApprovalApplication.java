package com.naviworks.invt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class InvtApprovalApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(InvtApprovalApplication.class, args);
	}

}
