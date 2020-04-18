package com.xie.program.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xie.program.entity.Student;

@Service
public interface StudentService {
	public List<Student> findList(Map queryMap);
	public int edit(Student student);
	public int del(String ids);
	public int add(Student student);
	public int total(Map queryMap);
	
	public Student findStu(String sno);
}
