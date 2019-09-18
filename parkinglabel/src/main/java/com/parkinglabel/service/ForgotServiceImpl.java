package com.parkinglabel.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.parkinglabel.dao.ForgotDao;
import com.parkinglabel.model.Forgot;

public class ForgotServiceImpl implements ForgotService {

	@Autowired
	ForgotDao forgotdao;
	
	@Override
	public void insertforgot(Forgot forgot) {
		forgotdao.insert(forgot);

	}

	@Override
	public void deleteforgot(Forgot forgot) {
		forgotdao.delete(forgot);
	}

	@Override
	public void updateforgot(Forgot forgot) {
		forgotdao.update(forgot);
	}

	@Override
	public Forgot findForgotByUniquekey(String key) {
		return forgotdao.findByUniquekey(key);
	}

	@Override
	public void deleteafterexpire(){
		forgotdao.deleteafterexpire();
	}
	
	@Override
	public boolean ismailalreadysent(int userid){
		return forgotdao.ismailalreadysent(userid);
	}
}
