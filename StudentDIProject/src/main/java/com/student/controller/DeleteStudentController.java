package com.student.controller;

import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.student.DIContainer;
import com.student.service.StudentService;

public class DeleteStudentController implements Controller {

	@Override
	public void execute(Scanner sc) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DIContainer.class);
		StudentService service = (StudentService) ctx.getBean("service");
		System.out.println("학생정보 삭제를 시작합니다...........");

		System.out.print("삭제할 학번 입력 : ");
		String sno = sc.nextLine();
		
		service.deleteStudent(sno);
	}

}
