package com.xie.program.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xie.program.dao.GradeDao;
import com.xie.program.entity.Grade;
import com.xie.program.entity.Grade;
import com.xie.program.service.GradeService;

@Service
public class GradeServiceImpl implements GradeService{
	@Autowired
	private GradeDao gradeDao;
	
	public int addGrade(Grade grade) {
		// TODO Auto-generated method stub
		return gradeDao.addGrade(grade);
	}
	public List<Grade> findList(Map queryMap) {
		// TODO Auto-generated method stub
		return gradeDao.findList(queryMap);
	}
	public int total(Map queryMap) {
		// TODO Auto-generated method stub
		return gradeDao.total(queryMap);
	}
	public int editGrade(Grade grade) {
		// TODO Auto-generated method stub
		return gradeDao.editGrade(grade);
	}
	public int delGrade(String ids) {
		// TODO Auto-generated method stub
		return gradeDao.delGrade(ids);
	}
	public Grade findByName(String name) {
		// TODO Auto-generated method stub
		return gradeDao.findByName(name);
	}
	public List<Grade> findAll() {
		// TODO Auto-generated method stub
		return gradeDao.findAll();
	}
	
}
