package com.expense.tracker.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Requestdto {
	private String userName;
	private String branch;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public Requestdto(String userName, String branch) {
		super();
		this.userName = userName;
		this.branch = branch;
	}
	public Requestdto() {
		super();
		
	}
	

	

	
	
	
	
	
	

}
