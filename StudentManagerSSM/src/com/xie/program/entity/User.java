package com.xie.program.entity;

import org.springframework.stereotype.Component;

/**
 * 用户实体类
 * @author Xiebuduo
 *
 */
@Component
public class User {
	private Long id;//主键、自增
	private String Username;//用户名
	private String Password;//密码
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
}
