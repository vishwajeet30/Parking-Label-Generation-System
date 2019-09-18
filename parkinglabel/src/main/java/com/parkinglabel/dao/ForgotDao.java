package com.parkinglabel.dao;

import com.parkinglabel.model.Forgot;

public interface ForgotDao {
	
	void insert(Forgot forgot);
	
	void delete(Forgot forgot);
	
	void update(Forgot forgot);
	
	Forgot findByUniquekey(String key);
	
	void deleteafterexpire();
	
	boolean ismailalreadysent(int userid);

}
