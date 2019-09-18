package com.parkinglabel.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.parkinglabel.model.LabelDocument;

public class LabelDocumentRowMapper implements RowMapper<LabelDocument> {  
	  
	 @Override  
	 public LabelDocument mapRow(ResultSet resultSet, int line) throws SQLException {  
	  LabelDocumentExtractor labelDocumentExtractor = new LabelDocumentExtractor();  
	  return labelDocumentExtractor.extractData(resultSet);  
	 }  
	  
	}  

