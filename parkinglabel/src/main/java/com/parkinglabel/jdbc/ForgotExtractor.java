package com.parkinglabel.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.parkinglabel.model.Forgot;

public class ForgotExtractor implements ResultSetExtractor<Forgot> {

	public Forgot extractData(ResultSet resultSet) throws SQLException,  
	   DataAccessException {  
	    
	  Forgot forgot = new Forgot();  
	    
	  forgot.setUserid(resultSet.getInt(1));
	  forgot.setUniquekey(resultSet.getString(2));
	  
	  return forgot;  
	 }
}
