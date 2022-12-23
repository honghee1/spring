package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.StudentDTO;

@Mapper
public interface StudentMapper {

	List<StudentDTO> searchStudent(Map<String, Object> map);

	List<StudentDTO> selectAllStudent();

}