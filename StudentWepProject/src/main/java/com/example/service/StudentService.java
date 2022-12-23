package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.StudentDTO;
import com.example.mapper.StudentMapper;

@Service
public class StudentService {
	private StudentMapper mapper;

	public StudentService(StudentMapper mapper) {
		this.mapper = mapper;
	}

	public List<StudentDTO> searchStudent(String kind, String search) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kind", kind);
		map.put("search", search);
		return mapper.searchStudent(map);
	}

	public List<StudentDTO> selectAllStudent() {
		return mapper.selectAllStudent();
	}
	
	
}
