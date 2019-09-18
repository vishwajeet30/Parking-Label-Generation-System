package com.parkinglabel.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.parkinglabel.model.LabelDocument;

public class LabelDocumentExtractor implements ResultSetExtractor<LabelDocument> {  
	  
	 public LabelDocument extractData(ResultSet resultSet) throws SQLException,  
	   DataAccessException {  
	    
	  LabelDocument doc = new LabelDocument();  
	    
	  doc.setId(resultSet.getInt(1));
	  doc.setLabelid(resultSet.getInt(2));
	  doc.setName(resultSet.getString(3));
	  doc.setDescription(resultSet.getString(4));  
	  doc.setType(resultSet.getString(5));  
	  doc.setContent(resultSet.getBytes(6));
	  
	  return doc;  
	 }
	 
	}
