package com.xie.program.page;

import org.springframework.stereotype.Component;

/**
 * ·ÖÒ³
 * @author Xiebuduo
 *
 */
@Component
public class Page {
	private int page;
	private int rows;
	private int offSet;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getOffSet() {
		this.offSet = (page-1)*rows;
		return offSet;
	}
	public void setOffSet(int offSet) {
		this.offSet = (page-1)*rows;
	}
	
	
}
