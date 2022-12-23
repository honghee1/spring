package com.student.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.student.dao.StudentDAO;
import com.student.exception.StudentException;
import com.student.vo.StudentVO;

public class StudentService {
	private StudentDAO dao;
		
	public StudentService(StudentDAO dao) {
		this.dao = dao;
	}

	public ArrayList<StudentVO> selectAllStudent() throws StudentException{
		ArrayList<StudentVO> list = dao.selectAllStudent();
		
		if(list.isEmpty())
			throw new StudentException("학생 데이터가 하나도 없습니다.");
		return list;
	}
	
	public StudentVO selectStudent(String sno) throws StudentException {
		StudentVO vo = dao.selectStudent(sno);
		
		if(vo == null) throw new StudentException("검색 결과 해당 학생 정보가 없습니다.");
		
		return vo;
	}

	public int insertStudent(StudentVO vo) {
		return dao.insertStudent(vo);
	}

	public int updateStudent(StudentVO vo) {
		return dao.updateStudent(vo);
	}

	public int deleteStudent(String sno) {
		return dao.deleteStudent(sno);
	}

	public ArrayList<StudentVO> selectRankOne() throws StudentException {
		ArrayList<StudentVO> list = dao.selectRankOne();
		
		if(list.isEmpty())
			throw new StudentException("학생 데이터가 하나도 없습니다.");
		return list;
	}

	public HashMap<String, Double> selectMajorAvgSocre() {
		return dao.selectMajorAvgSocre();
	}

	public ArrayList<HashMap<String, Object>> selectStudentScholarship() {
		return dao.selectStudentScholarship();
	}
	
}










