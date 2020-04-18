package com.xie.program.entity;

import org.springframework.stereotype.Component;

/**
 * °ΰΌ¶ΚµΜε
 * @author Xiebuduo
 *
 */
@Component
public class Clazz {
	private Long id;
	private Long gid;
	private String name;
	private String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
