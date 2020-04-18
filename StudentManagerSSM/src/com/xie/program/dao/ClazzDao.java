package com.xie.program.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xie.program.entity.Clazz;

@Repository
public interface ClazzDao {
	public int addClazz(Clazz clazz);
	public int editClazz(Clazz clazz);
	public int delClazz(String ids);
	
	public Clazz findByName(Map map);
	public List<Clazz> findList(Map queryMap);
	public List<Clazz> findAll();
	public int total(Map queryMap);
}
