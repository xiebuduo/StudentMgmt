package com.xie.program.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xie.program.dao.UserDao;
import com.xie.program.entity.User;
import com.xie.program.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	public int addUser(User user) {
		return userDao.addUser(user);
	}

	public List<User> findList(Map queryMap) {
		// TODO Auto-generated method stub
		return userDao.findList(queryMap);
	}

	public int total(Map queryMap) {
		// TODO Auto-generated method stub
		return userDao.total(queryMap);
	}

	public int editUser(User user) {
		// TODO Auto-generated method stub
		return userDao.editUser(user);
	}

	public int delUser(String ids) {
		// TODO Auto-generated method stub
		return userDao.delUser(ids);
	}

}
