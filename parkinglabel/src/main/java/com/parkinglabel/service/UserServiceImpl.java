package com.parkinglabel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.parkinglabel.dao.UserDao;
import com.parkinglabel.model.User;

public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userdao;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public User findById(int id) {
		return userdao.findById(id);
	}

	@Override
	public User findByUsername(String username) {
		return userdao.findByUsername(username);
	}

	@Override
	public void saveUser(User user) {
		user.setVerified(true);
		user.setRole("USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userdao.save(user);

	}

	@Override
	public void updateUser(User user) {
		userdao.update(user);

	}

	@Override
	public void deleteUserByUsername(String username) {
		userdao.deleteByUsername(username);

	}

	@Override
	public List<User> findAllUsers() {
		return userdao.findAllUsers();
	}

	@Override
	public boolean isUserUsernameUnique(String username) {
		List<User> users = userdao.findAllByUsername(username);
        if(users.size()>0){
        	return false;
        }
        else{
        	return true;
        }
	}
	
	
	
	@Override
	public boolean isMobileUnique(String mobile) {
		List<User> users = userdao.findAllByMobile(mobile);
        if(users.size()>0){
        	return false;
        }
        else{
        	return true;
        }
	}
	
	@Override
	public boolean isEmailUnique(String email) {
		List<User> users = userdao.findAllByEmail(email);
        if(users.size()>0){
        	return false;
        }
        else{
        	return true;
        }
	}
	
	public List<User> searchusingusername(String word){
		return userdao.searchusingusername(word);
	}
	
	@Override
	public boolean isUsernameValid(String username) {
		List<User> users = userdao.findAllByUsername(username);
        if(users.size()>0){
        	return true;
        }
        else{
        	return false;
        }
	}
	
	
	@Override
	public void updatePassword(int userid,String password) {
		String encodedpassword=passwordEncoder.encode(password);
		userdao.updatePassword(userid,encodedpassword);
	}
}
