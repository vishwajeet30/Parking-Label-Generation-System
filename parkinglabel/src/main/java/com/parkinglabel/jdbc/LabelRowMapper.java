package com.parkinglabel.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.parkinglabel.model.Label;

public class LabelRowMapper implements RowMapper<Label> {  
	  
	 @Override  
	 public Label mapRow(ResultSet resultSet, int line) throws SQLException {  
	  LabelExtractor labelExtractor = new LabelExtractor();  
	  return labelExtractor.extractData(resultSet);  
	 }  
	  
	}  

