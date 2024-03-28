package com.expense.tracker.enums;

public enum FriendshipEnum {

	Pending(1L, "Pending"), Accepted(2L, "Accepted"), Rejected(3L, "Rejected");

	private Long id;
	private String name;

	private FriendshipEnum(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
