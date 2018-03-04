package com.rpramadhan.sbspringdata.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author rpramadhan
 * Enter point for Liquibase Application
 */
@Profile("test")
@SpringBootApplication
@ComponentScan("com.rpramadhan.sbspringdata")
@EntityScan({"com.rpramadhan.sbspringdata.model"})
@EnableJpaRepositories({"com.rpramadhan.sbspringdata.repository"})
@ActiveProfiles("test")
public class SBSpringDataTest {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SBSpringDataTest.class, args);
	}

}
