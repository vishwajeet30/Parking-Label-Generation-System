package com.parkinglabel.service;

import com.parkinglabel.model.Forgot;

public interface ForgotService {
	
	void insertforgot(Forgot forgot);
	
	void deleteforgot(Forgot forgot);
	
	void updateforgot(Forgot forgot);
	
	Forgot findForgotByUniquekey(String key);
	
	void deleteafterexpire();
	
	boolean ismailalreadysent(int userid);

}
