package com.student.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.student.DIContainer;
import com.student.service.StudentService;

public class SelectMajorAvgScoreController implements Controller {

	@Override
	public void execute(Scanner sc) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DIContainer.class);
		StudentService service = (StudentService) ctx.getBean("service");
		HashMap<String, Double> map = 
				service.selectMajorAvgSocre();
		
		Set<String> key = map.keySet();
		
		Iterator<String> it = key.iterator();
		
		while(it.hasNext()) {
			String k = it.next();
			System.out.println(k + " : " + map.get(k));
		}
	} 

}









