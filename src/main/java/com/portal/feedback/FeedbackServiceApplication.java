package com.portal.feedback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.portal")
public class FeedbackServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedbackServiceApplication.class, args);
	}

	 @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }
}
