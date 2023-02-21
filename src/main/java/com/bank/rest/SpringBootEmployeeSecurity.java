package com.bank.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Jigyasu Garg
 * @since 20 02 23
 */

@SpringBootApplication
public class SpringBootEmployeeSecurity {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEmployeeSecurity.class, args);
		System.out.println("Securing employee rest application .......");
	}

}
