package com.xie.program.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xie.program.entity.Clazz;

/**
 * °à¼¶service
 * @author Xiebuduo
 *
 */
@Service
public interface ClazzService {
	public int addClazz(Clazz clazz);
	public int editClazz(Clazz clazz);
	public int delClazz(String ids);
	
	public Clazz findByName(Map map);
	public List<Clazz> findList(Map queryMap);
	public List<Clazz> findAll();
	public int total(Map queryMap);
}
