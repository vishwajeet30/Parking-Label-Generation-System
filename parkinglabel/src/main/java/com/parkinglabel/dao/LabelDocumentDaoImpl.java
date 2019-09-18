package com.parkinglabel.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.parkinglabel.jdbc.LabelDocumentRowMapper;
import com.parkinglabel.model.LabelDocument;

public class LabelDocumentDaoImpl implements LabelDocumentDao {

	
	@Autowired  
	DataSource dataSource;
	
	
	@Override
	public List<LabelDocument> findAll() {
		List<LabelDocument> docList = new ArrayList<LabelDocument>();  
		  
		String sql = "select * from documents";  
		  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  docList = jdbcTemplate.query(sql, new LabelDocumentRowMapper());  
		  return docList;
	}

	@Override
	public LabelDocument findById(int id) {
		List<LabelDocument> docList = new ArrayList<LabelDocument>();  
		  String sql = "select * from documents where id=" + id;  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  docList = jdbcTemplate.query(sql, new LabelDocumentRowMapper());  
		  return docList.get(0); 
	}

	@Override
	public void save(LabelDocument document) {
		String sql = "INSERT INTO documents "  
			    + "(label_id, name, description, type, content) VALUES (?,?,?,?,?)";  
			  
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
			  
		jdbcTemplate.update(sql,new Object[] { document.getLabelid(),document.getName()
				,document.getDescription(),document.getType(),document.getContent()});

	}

	@Override
	public List<LabelDocument> findAllByLabelId(int labelId) {
		List<LabelDocument> docList = new ArrayList<LabelDocument>();  
		  
		String sql = "select * from documents where label_id="+labelId;  
		  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  docList = jdbcTemplate.query(sql, new LabelDocumentRowMapper());  
		  return docList;
	}

	@Override
	public void deleteById(int id) {
		String sql = "delete from documents where id="+id;  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  jdbcTemplate.update(sql); 
	}
	
	@Override
	public void deleteAllByLabelId(int id) {
		String sql = "delete from documents where label_id="+id;
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		  jdbcTemplate.update(sql); 
	}

}
