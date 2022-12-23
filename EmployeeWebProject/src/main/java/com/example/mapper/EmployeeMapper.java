package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.EmployeeDTO;

@Mapper
public interface EmployeeMapper {

	List<EmployeeDTO> searchEmployee(Map<String, Object> map);

	List<EmployeeDTO> selectAllEmployee();

}
