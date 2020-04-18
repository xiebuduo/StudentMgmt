package com.xie.program.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xie.program.dao.StudentDao;
import com.xie.program.entity.Student;
import com.xie.program.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;
	
	public List<Student> findList(Map queryMap) {
		return studentDao.findList(queryMap);
	}

	public int edit(Student student) {
		return studentDao.edit(student);
	}

	public int del(String ids) {
		return studentDao.del(ids);
	}

	public int add(Student student) {
		return studentDao.add(student);
	}

	public int total(Map queryMap) {
		return studentDao.total(queryMap);
	}

	public Student findStu(String sno) {
		return studentDao.findStu(sno);
	}

}
