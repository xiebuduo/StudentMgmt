package com.xie.program.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xie.program.entity.User;

@Repository
public interface UserDao {
	public User findByUserName(String userName);
	public int addUser(User user);
	public List<User> findList(Map queryMap);
	public int total(Map queryMap);
	public int editUser(User user);
	public int delUser(String ids);
}
