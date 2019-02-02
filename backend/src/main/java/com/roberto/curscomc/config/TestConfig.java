package com.roberto.curscomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.roberto.curscomc.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService DBService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
			DBService.instantiateTestDatabase();
		return true;
	}
}
