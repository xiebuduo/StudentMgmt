package com.xie.program.entity;

import org.springframework.stereotype.Component;

/**
 * �û�ʵ����
 * @author Xiebuduo
 *
 */
@Component
public class User {
	private Long id;//����������
	private String Username;//�û���
	private String Password;//����
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
