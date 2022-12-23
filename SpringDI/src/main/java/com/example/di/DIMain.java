package com.example.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DIMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx
		= new AnnotationConfigApplicationContext(DIContainer.class);
		
		Greeting g1 = ctx.getBean("greeter", Greeting.class);
		System.out.println(g1);
		Greeting g2 = ctx.getBean("greeter", Greeting.class);
		System.out.println(g2);
		System.out.println(g1 == g2);
		
		MemberService m1 = ctx.getBean("service", MemberService.class);
		MemberService m2 = ctx.getBean("service", MemberService.class);
		m1.selectMember();
		m2.selectMember();
		System.out.println(m1 == m2);
	}

}
