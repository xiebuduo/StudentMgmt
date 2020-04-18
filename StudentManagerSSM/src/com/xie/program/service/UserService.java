package com.xie.program.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xie.program.entity.User;

@Service
public interface UserService {
	public User findByUserName(String userName);
	public int addUser(User user);
	public List<User> findList(Map queryMap);
	public int total(Map queryMap);
	public int editUser(User user);
	public int delUser(String ids);
}
