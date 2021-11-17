package org.danyuan.application;

import org.danyuan.application.service.FileDelHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppendonlyHelpApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(AppendonlyHelpApplication.class, args);
	}

	@Autowired
	FileDelHelp fileDelHelp;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		fileDelHelp.run();
	}

}
