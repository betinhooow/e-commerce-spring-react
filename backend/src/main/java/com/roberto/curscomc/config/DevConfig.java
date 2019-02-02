package com.roberto.curscomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.roberto.curscomc.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService DBService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if(!"create".equals(strategy)) {
			return false;
		}
		
		DBService.instantiateTestDatabase();
		return true;
	}
}
