package org.forksmash.remotenumberbot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class RemoteNumberbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemoteNumberbotApplication.class, args);
	}

}
