package com.xie.program.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xie.program.dao.ClazzDao;
import com.xie.program.entity.Clazz;
import com.xie.program.service.ClazzService;

@Service
public class ClazzServiceImpl implements ClazzService {
	
	@Autowired
	private ClazzDao clazzDao;


	public int addClazz(Clazz clazz) {
		return clazzDao.addClazz(clazz);
	}

	public int editClazz(Clazz clazz) {
		return clazzDao.editClazz(clazz);
	}

	public int delClazz(String ids) {
		return clazzDao.delClazz(ids);
	}

	public Clazz findByName(Map map) {
		return clazzDao.findByName(map);
	}

	public List<Clazz> findList(Map queryMap) {
		return clazzDao.findList(queryMap);
	}

	public List<Clazz> findAll() {
		return clazzDao.findAll();
	}

	public int total(Map queryMap) {
		return clazzDao.total(queryMap);
	}

}
