package com.expense.tracker.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SearchDto {

	private String serchKeys;
	
	private Long userId;
	
	private Long catagoryId; 
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public String getSerchKeys() {
		return serchKeys;
	}

	public void setSerchKeys(String serchKeys) {
		this.serchKeys = serchKeys;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCatagoryId() {
		return catagoryId;
	}

	public void setCatagoryId(Long catagoryId) {
		this.catagoryId = catagoryId;
	}
	
	
	
	
	
	
	
	
}