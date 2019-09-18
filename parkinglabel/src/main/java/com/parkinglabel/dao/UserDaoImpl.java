package com.parkinglabel.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.parkinglabel.jdbc.UserRowMapper;
import com.parkinglabel.model.User;

public class UserDaoImpl implements UserDao {

	@Autowired  
	DataSource dataSource;  
	
	@Override
	public User findById(int id) {
		  List<User> userList = new ArrayList<User>();  
		  String sql = "select * from users where id=" + id;  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  userList = jdbcTemplate.query(sql, new UserRowMapper());  
		  return userList.get(0);  
	}

	@Override
	public User findByUsername(String username) {
		  List<User> userList = new ArrayList<User>();  
		  String sql = "select * from users where username='" +username+"'";  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  userList = jdbcTemplate.query(sql, new UserRowMapper());  
		  return userList.get(0);  
	}
	
	@Override
	public List<User> findAllByUsername(String username) {
		  List<User> userList = new ArrayList<User>();  
		  String sql = "select * from users where username='" +username+"'";  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  userList = jdbcTemplate.query(sql, new UserRowMapper());  
		  return userList;
	}
	
	@Override
	public List<User> findAllByMobile(String mobile) {
		  List<User> userList = new ArrayList<User>();  
		  String sql = "select * from users where mobile='" +mobile+"'";  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  userList = jdbcTemplate.query(sql, new UserRowMapper());  
		  return userList;
	}
	
	@Override
	public List<User> findAllByEmail(String email) {
		  List<User> userList = new ArrayList<User>();  
		  String sql = "select * from users where email='" +email+"'";  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  userList = jdbcTemplate.query(sql, new UserRowMapper());  
		  return userList;
	}

	@Override
	public void save(User user) {
		String sql = "INSERT INTO users "  
			    + "(username, email, name, desg, divison, telephone, mobile, address, password, isVerified, role) VALUES (?,?,?,?,?,?,?,?,?,?,?)";  
			  
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
			  
		jdbcTemplate.update(sql,new Object[] { user.getUsername(), user.getEmail(), user.getName()
				, user.getDesg(), user.getDivision(), user.getTelephone(), user.getMobile()
				,user.getAddress(),user.getPassword(),user.isVerified(),user.getRole()});  
	}

	@Override
	public void deleteByUsername(String username) {
		String sql = "delete from users where username='" + username+"'";  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  jdbcTemplate.update(sql);  
		  

	}

	@Override
	public List<User> findAllUsers() {
		List<User> userList = new ArrayList<User>();  
		  
		String sql = "select * from users";  
		  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  userList = jdbcTemplate.query(sql, new UserRowMapper());  
		  return userList;  
	}
	
	
	@Override
	public void update(User user){
		String sql = "UPDATE IGNORE users set email=?, name=?, desg=?, divison=?, telephone=?, mobile=?, address=?, role=? where id="+user.getId();  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  
		  jdbcTemplate.update(  
		    sql,  
		    new Object[] { user.getEmail(), user.getName()
					, user.getDesg(), user.getDivision(), user.getTelephone(), user.getMobile()
					,user.getAddress(), user.getRole() });  
		  
	}
	
	
	@Override
	public List<User> searchusingusername(String word){
		List<User> userList = new ArrayList<User>();  
		  
		String sql = "select * from users where username like '%"+word+"%'";  
		  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  userList = jdbcTemplate.query(sql, new UserRowMapper());  
		  return userList;
	}
	
	@Override
	public void updatePassword(int userid,String password){
		String sql="update users set password='"+password+"' where id="+userid;
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql);
	}
}
