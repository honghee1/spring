package com.example;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.dto.EmployeeDTO;
import com.example.mapper.EmployeeMapper;

@SpringBootTest
class EmployeeWebProjectApplicationTests {
	@Autowired
	EmployeeMapper mapper;

	@DisplayName("전체 조회 테스트")
	@Test
	void testSelectAllEmployee() {
		List<EmployeeDTO> list = mapper.selectAllEmployee();
		System.out.println(list);
		assertNotEquals(0, list.size(), "전체 조회 오류");
	}

}
