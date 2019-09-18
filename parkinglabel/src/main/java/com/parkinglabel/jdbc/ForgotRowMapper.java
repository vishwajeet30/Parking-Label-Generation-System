package com.parkinglabel.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.parkinglabel.model.Forgot;

public class ForgotRowMapper implements RowMapper<Forgot> {

	 @Override  
	 public Forgot mapRow(ResultSet resultSet, int line) throws SQLException {  
	  ForgotExtractor forgotExtractor = new ForgotExtractor();  
	  return forgotExtractor.extractData(resultSet);  
	 }  
}
