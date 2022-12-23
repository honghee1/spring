package com.example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	private MainService service;
	private PersonDTO person;
	
	public MainController(MainService service, PersonDTO person) {
		this.service = service;
		this.person = person;
	}

	@RequestMapping("/")
	public String test() {
		System.out.println("test()");
		service.testService();
		System.out.println(person.toString());
		return "test";
	}
	
	@RequestMapping("/form.do")
	public String from(HttpServletRequest request) {
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		System.out.println(name + " " + age);
		request.setAttribute("test", "셋팅한 데이터");
		return "result";
	}
	
	@RequestMapping("/registerView.do")
	public String registerView(HttpServletRequest request) {
		return "register";
	}
	
//	@RequestMapping("/register.do")
//	public String register(HttpServletRequest request) {
//		String id = request.getParameter("id");
//		String pass = request.getParameter("pass");
//		String name = request.getParameter("name");
//		int age = Integer.parseInt(request.getParameter("age"));
//		
//		System.out.println(id);
//		System.out.println(pass);
//		System.out.println(name);
//		System.out.println(age);
//		return null;
//	}
	
//	@RequestMapping("/register.do")
//	public String register(String id, String pass, String name, int age) {
//		RegisterDTO dto = new RegisterDTO(id, pass, name, age);
//		
//		System.out.println(dto);
//		return null;
//	}

	@RequestMapping("/register.do")
	public String register(RegisterDTO dto, Model model) { 
		System.out.println(dto);
		model.addAttribute("dto", dto); 
		return "register_result";
	}
	
	@RequestMapping("/loginView.do")
	public String loginView() {
		return "login";
	}
	
	@RequestMapping("login.do") 
	public String login(String id, String pass, HttpSession session) {
		session.setAttribute("id", id);
		session.setAttribute("pass", pass);
		return "login_result";
	}
	
	
}











