package org.forksmash.remotenumberbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RemoteNumberbotApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(RemoteNumberbotApplication.class).web(WebApplicationType.SERVLET).run(args);
	}

}
