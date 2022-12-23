package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository // @Component를 사용해도됨
public class PersonDTO {
	private String name;
	private int age;
	
	public PersonDTO(@Value("홍길동") String name,@Value("22") int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "PersonDTO [name=" + name + ", age=" + age + "]";
	}
	
	
}
