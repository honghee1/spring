package com.student.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.student.DIContainer;
import com.student.service.StudentService;

public class SelectSchoolScholarshipController implements Controller {

	@Override
	public void execute(Scanner sc) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DIContainer.class);
		StudentService service = (StudentService) ctx.getBean("service");
		ArrayList<HashMap<String, Object>> list = 
				service.selectStudentScholarship();
		
		for(int i=0;i<list.size();i++) {
			HashMap<String, Object> map = list.get(i);
			System.out.println(map.get("sno") + " " + map.get("sname") 
			+ " " + map.get("major_name") + " " + map.get("price"));
		}
	}

}









