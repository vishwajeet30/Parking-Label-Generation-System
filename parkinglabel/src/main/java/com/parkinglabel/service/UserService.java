package com.parkinglabel.service;

import java.util.List;


import com.parkinglabel.model.User;


public interface UserService {
	
	User findById(int id);
    
    User findByUsername(String username);
     
    void saveUser(User user);
     
    void updateUser(User user);
     
    void deleteUserByUsername(String username);
 
    List<User> findAllUsers(); 
     
    boolean isUserUsernameUnique(String username);
    
    boolean isMobileUnique(String mobile);
    
    boolean isEmailUnique(String email);
    
    List<User> searchusingusername(String word);
    
    boolean isUsernameValid(String username);

    void updatePassword(int userid,String password);
}
