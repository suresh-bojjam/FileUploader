/*
 * https://github.com/spring-guides
 * https://commons.apache.org/proper/commons-fileupload/
 * */

package com.content.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.content.storage.StorageProperties;
import com.content.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@ComponentScan(basePackages = {
        "com.content.filerepo",
        "com.content.storage"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//storageService.deleteAll();
			try{storageService.init();}catch(Exception e) {System.out.println(e.getMessage());}
		};
	}
}