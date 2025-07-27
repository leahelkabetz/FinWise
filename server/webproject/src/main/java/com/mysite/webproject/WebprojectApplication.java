package com.mysite.webproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableScheduling
public class WebprojectApplication {

	public static void main(String[] args) {
	    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		System.setProperty("GMAIL_USERNAME", dotenv.get("GMAIL_USERNAME"));
        System.setProperty("GMAIL_PASSWORD", dotenv.get("GMAIL_PASSWORD"));
		SpringApplication.run(WebprojectApplication.class, args);
	}

}
