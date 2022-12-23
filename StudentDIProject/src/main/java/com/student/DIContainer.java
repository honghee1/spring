package com.student;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.student.config.DBManager;
import com.student.controller.HandlerMapping;
import com.student.dao.StudentDAO;
import com.student.service.StudentService;

@Configuration
public class DIContainer {
	
	@Bean
	public HandlerMapping handler() {
		return new HandlerMapping();
	}
	
	@Bean
	public DBManager dbmanager() {
		return new DBManager();
	}
	
	@Bean
	public StudentDAO dao() {
		return new StudentDAO(dbmanager());
	}
	
	@Bean
	public StudentService service() {
		return new StudentService(dao());
	}
	
}