package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.EmployeeDTO;
import com.example.mapper.EmployeeMapper;

@Service
public class EmployeeService {
	private EmployeeMapper mapper;

	public EmployeeService(EmployeeMapper mapper) {
		this.mapper = mapper;
	}

	public List<EmployeeDTO> searchEmployee(String kind, String search) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kind", kind);
		if(kind.equals("position"))
			map.put("search", Integer.parseInt(search));
		else
			map.put("search", search);
		return mapper.searchEmployee(map);
	}

	public List<EmployeeDTO> selectAllEmployee(String kind, String search) {
		return mapper.selectAllEmployee();
	}
	
	
}
