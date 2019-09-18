package com.parkinglabel.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.parkinglabel.jdbc.ForgotRowMapper;
import com.parkinglabel.model.Forgot;

public class ForgotDaoImpl implements ForgotDao {
	
	@Autowired  
	DataSource dataSource;

	@Override
	public void insert(Forgot forgot) {
		String sql = "INSERT INTO forgot (userid, uniquekey) VALUES (?,?)";  
			  
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
			  
		jdbcTemplate.update(sql,new Object[] { forgot.getUserid(), forgot.getUniquekey()});  
	}

	@Override
	public void delete(Forgot forgot) {
		String sql = "delete from forgot where userid=" +forgot.getUserid();  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  jdbcTemplate.update(sql);  
	}

	@Override
	public void update(Forgot forgot) {
		String sql = "update forgot set uniquekey='"+forgot.getUniquekey()+"' where userid=" +forgot.getUserid();  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  jdbcTemplate.update(sql); 
	}
	
	@Override
	public Forgot findByUniquekey(String key) {
		List<Forgot> forgotList = new ArrayList<Forgot>();  
		  String sql = "select * from forgot where uniquekey='"+key+"'";  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  forgotList = jdbcTemplate.query(sql, new ForgotRowMapper());  
		  return forgotList.get(0);  
	}
	
	@Override
	public void deleteafterexpire(){
		String sql="delete from forgot where TIMESTAMPDIFF(hour,keytimestamp, current_timestamp())>24";
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql);
	}
	
	
	@Override
	public boolean ismailalreadysent(int userid){
		String sql = "select * from forgot where userid="+userid;  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  List<Forgot> forgotList = new ArrayList<Forgot>();  
		  forgotList = jdbcTemplate.query(sql, new ForgotRowMapper());
		  if(forgotList.size()>0){
			  return false;
		  }
		  else{
			  return false;
		  }
	}

}
