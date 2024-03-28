package com.expense.tracker.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Pagination<T> {

	private Integer pageSize;
	private Integer pageNumber;
	private Integer totalPages;
	private Integer numberOfElements;
	private Integer totalElements;
	private T data;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(Integer numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public Integer getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}