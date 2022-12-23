package com.example;

import org.springframework.stereotype.Service;

@Service // 싱글톤 적용
public class MainService {
	
	public void testService() {
		System.out.println("testService method");
	}
}
