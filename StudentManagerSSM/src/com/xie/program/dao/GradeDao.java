package com.xie.program.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xie.program.entity.Grade;
import com.xie.program.entity.Grade;

@Repository
public interface GradeDao {
	public List<Grade> findList(Map queryMap);
	public int addGrade(Grade grade);
	public int total(Map queryMap);
	public int editGrade(Grade grade);
	public int delGrade(String ids);
	public Grade findByName(String name);
	public List<Grade> findAll();
}
