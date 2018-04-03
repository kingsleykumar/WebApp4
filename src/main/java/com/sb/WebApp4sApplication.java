package com.sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WebApp4sApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = SpringApplication.run(WebApp4sApplication.class, args);
//		ctx.addApplicationListener(new LogoutListener());
	}
}
