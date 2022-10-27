package com.assignment;

import java.security.Security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class AssignmentJava6Application {

	public static void main(String[] args) {
		// them dong nay khi co loi TSL:
		Security.setProperty("jdk.tls.disabledAlgorithms",
				"SSLv3, RC4, DES, MD5withRSA, DH keySize < 1024,EC keySize < 224, 3DES_EDE_CBC, anon, NULL");
		SpringApplication.run(AssignmentJava6Application.class, args);
	}

}
