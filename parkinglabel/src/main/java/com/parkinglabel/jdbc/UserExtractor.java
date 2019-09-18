package com.parkinglabel.jdbc;

import java.sql.ResultSet;  
import java.sql.SQLException;  
import org.springframework.dao.DataAccessException;  
import org.springframework.jdbc.core.ResultSetExtractor;  
import com.parkinglabel.model.User;  
  
public class UserExtractor implements ResultSetExtractor<User> {  
  
 public User extractData(ResultSet resultSet) throws SQLException,  
   DataAccessException {  
    
  User user = new User();  
    
  user.setId(resultSet.getInt(1));
  user.setUsername(resultSet.getString(2));
  user.setEmail(resultSet.getString(3));
  user.setName(resultSet.getString(4));   
  user.setDesg(resultSet.getString(5));
  user.setDivision(resultSet.getString(6));
  user.setTelephone(resultSet.getString(7));
  user.setMobile(resultSet.getString(8));
  user.setAddress(resultSet.getString(9));
  user.setPassword(resultSet.getString(10));
  user.setVerified(resultSet.getBoolean(11));
  user.setRole(resultSet.getString(12));
  
  return user;  
 }
 
}