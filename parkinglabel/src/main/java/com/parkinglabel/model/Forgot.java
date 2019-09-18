package com.parkinglabel.model;

import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

public class Forgot {
	
	@Id
	private int userid;
	
	@NotEmpty
	private String uniquekey;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUniquekey() {
		return uniquekey;
	}

	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}

	
	
}
