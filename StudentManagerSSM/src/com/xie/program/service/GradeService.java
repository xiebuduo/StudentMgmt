package com.xie.program.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xie.program.entity.Grade;
@Service
public interface GradeService {
	public int addGrade(Grade grade);
	public List<Grade> findList(Map queryMap);
	public int total(Map queryMap);
	public int editGrade(Grade grade);
	public int delGrade(String ids);
	public Grade findByName(String name);
	public List<Grade> findAll();
}
