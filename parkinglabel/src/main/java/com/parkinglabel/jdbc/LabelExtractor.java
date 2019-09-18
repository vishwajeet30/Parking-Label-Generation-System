package com.parkinglabel.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.parkinglabel.model.Label;

public class LabelExtractor implements ResultSetExtractor<Label> {  
	  
	 public Label extractData(ResultSet resultSet) throws SQLException,  
	   DataAccessException {  
	    
	  Label label = new Label();  
	    
	  label.setId(resultSet.getInt(1));
	  label.setUser_id(resultSet.getInt(2));
	  label.setIssueid(resultSet.getString(3));
	  label.setName(resultSet.getString(4));   
	  label.setDesignation(resultSet.getString(5));
	  label.setDivision(resultSet.getString(6));
	  label.setTelephone(resultSet.getString(7));
	  label.setMobile(resultSet.getString(8));
	  label.setAddress(resultSet.getString(9));
	  label.setMake(resultSet.getString(10));
	  label.setModel(resultSet.getString(11));
	  label.setRelationship(resultSet.getString(12));
	  label.setRegisteredownername(resultSet.getString(13));
	  label.setRegisteredowneraddress(resultSet.getString(14));
	  label.setRegistrationnum(resultSet.getString(15));
	  label.setIcardnum(resultSet.getString(16));
	  label.setStartdate(resultSet.getDate(17));
	  label.setEnddate(resultSet.getDate(18));
	  label.setLabeltype(resultSet.getString(19));
	  label.setStatus(resultSet.getString(20));
	  label.setHiredtaxi(resultSet.getBoolean(21));
	  label.setSignedformuploaded(resultSet.getBoolean(22));
	  label.setRemarks(resultSet.getString(23));
	  label.setEdited(resultSet.getBoolean(24));
	  return label;  
	 }
	 
	}
