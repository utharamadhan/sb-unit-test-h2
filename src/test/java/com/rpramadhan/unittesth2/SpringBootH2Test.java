package com.rpramadhan.unittesth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author rpramadhan
 * Enter point for Unit Test H2 Application
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.rpramadhan.unittesth2"}, excludeFilters={
		  @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=SpringBootH2Main.class)})
@EntityScan({"com.rpramadhan.unittesth2.model"})
public class SpringBootH2Test {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootH2Test.class, args);
	}

}
