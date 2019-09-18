package com.parkinglabel.dao;

import java.util.List;


import com.parkinglabel.model.User;
 
 
public interface UserDao {
    
 
    User findById(int id);
     
    User findByUsername(String username);
     
    void save(User user);
     
    void deleteByUsername(String username);
     
    List<User> findAllUsers();
    
    void update(User user);
    
    List<User> findAllByUsername(String username);
    
    List<User> findAllByMobile(String mobile);
    
    List<User> findAllByEmail(String email);
    
    List<User> searchusingusername(String word);
 
    void updatePassword(int userid,String password);
}
