package com.parkinglabel.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.parkinglabel.jdbc.LabelRowMapper;
import com.parkinglabel.model.Label;

public class LabelDaoImpl implements LabelDao {

	@Autowired  
	DataSource dataSource;
	
	@Override
	public Label findById(int id) {
		List<Label> labelList = new ArrayList<Label>();  
		String sql = "select * from labels where id=" + id;  
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		labelList = jdbcTemplate.query(sql, new LabelRowMapper());  
		return labelList.get(0);
	}

	@Override
	public Label findByIssueId(String issueid) {
		List<Label> labelList = new ArrayList<Label>();  
		  String sql = "select * from labels where issue_id='" +issueid+"'";  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  labelList = jdbcTemplate.query(sql, new LabelRowMapper());  
		  return labelList.get(0);  
	}

	@Override
	public int save(Label label) {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		
		
		jdbcInsert.withTableName("labels").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("user_id", label.getUser_id());
        parameters.put("name",label.getName());
        parameters.put("designation",label.getDesignation());
        parameters.put("division",label.getDivision());
        parameters.put("telephone",label.getTelephone());
        parameters.put("mobile",label.getMobile());
        parameters.put("address",label.getAddress());
        parameters.put("make",label.getMake());
        parameters.put("model",label.getModel());
        parameters.put("relationship",label.getRelationship());
        parameters.put("registeredownername",label.getRegisteredownername());
        parameters.put("registeredowneraddress",label.getRegisteredowneraddress());
        parameters.put("registrationnum",label.getRegistrationnum());
        parameters.put("icardnum",label.getIcardnum());
        parameters.put("labeltype",label.getLabeltype());
        parameters.put("labelstatus",label.getStatus());
        parameters.put("hiredtaxi",label.isHiredtaxi());
        parameters.put("issignedformuploaded",label.isSignedformuploaded());
        // execute insert
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
           // convert Number to Int using ((Number) key).intValue()
            return ((Number) key).intValue();
	}

	@Override
	public void deleteById(int id) {
		String sql = "delete from labels where id=" +id;  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  jdbcTemplate.update(sql);  

	}

	@Override
	public List<Label> findAllLabels() {
		List<Label> labelList = new ArrayList<Label>();  
		  
		String sql = "select * from labels";  
		  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  labelList = jdbcTemplate.query(sql, new LabelRowMapper());  
		  return labelList;  
	}

	@Override
	public List<Label> findAllByUserId(int userid) {
		List<Label> labelList = new ArrayList<Label>();  
		  
		String sql = "select * from labels where user_id="+userid;  
		  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  labelList = jdbcTemplate.query(sql, new LabelRowMapper());  
		  return labelList;
	}

	@Override
	public List<Label> findAllIssueRequestLabels() {
		List<Label> labelList = new ArrayList<Label>();  
		  
		String sql = "select * from labels where labelstatus='Issue In Progress'";  
		  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  labelList = jdbcTemplate.query(sql, new LabelRowMapper());  
		  return labelList;
	}

	@Override
	public List<Label> findAllRenewRequestLabels() {
		List<Label> labelList = new ArrayList<Label>();  
		  
		String sql = "select * from labels where labelstatus='Renew In Progress'";  
		  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  labelList = jdbcTemplate.query(sql, new LabelRowMapper());  
		  return labelList;
	}

	
	@Override
	public void update(Label label){
		String sql = "UPDATE labels set issue_id=?,labelstatus=?,startdate=?,enddate=? where id="+label.getId();  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  
		  jdbcTemplate.update(sql, new Object[] { label.getIssueid(),label.getStatus(),label.getStartdate(),label.getEnddate() });  
		  
	}
	
	@Override
	public int countrequests() {
		String sql="select count(*) from labels where labelstatus='Issue In Progress' or labelstatus='Renew In Progress'";     
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public List<Label> search(String word, String collumn){
		List<Label> labelList = new ArrayList<Label>();  
		  
		String sql = "select * from labels where "+collumn+" like '%"+word+"%'";  
		  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  labelList = jdbcTemplate.query(sql, new LabelRowMapper());  
		  return labelList;
	}
	
	@Override
	public boolean isRegistrationNumUnique(String rn) {
		
		//Count only parkinglabels with with Offical Type as Non Official and Date Pass can request multiple issues with the same License Number once expired but Official will always have to renew
		String sql="select * from labels where registrationnum='"+rn+"'";     
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		List<Label> labelList = new ArrayList<Label>(); 
		labelList = jdbcTemplate.query(sql, new LabelRowMapper()); 
		if(labelList.size()==0){
			return true;
		}
		else{ //There Exists Labels with same Registration Number 
			for(Label label : labelList){
				if(label.getLabeltype().equals("Official")){
					return false; //If an Official Label is registered return false i.e. Another can't be registered
				}
				else{
					//If the existing label is not official check whether the status says expired, only if they all are expired he is allowed to request for another
					if(!label.getStatus().equals("Expired")){
						return false;
					}
				}
			}
			return true;
		}
		
	}
	
	
	//When User has Editing Privileges
	@Override	
	public void editlabel(Label label){
		String sql = "UPDATE labels set name=?,designation=?, division=?,telephone=?,mobile=?,address=?,make=?,model=?,relationship=?,registeredownername=?,registeredowneraddress=?,registrationnum=?,icardnum=?,labeltype=?,hiredtaxi=?,issignedformuploaded=?,isedited=? where id="+label.getId();  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		  jdbcTemplate.update(sql, new Object[] { label.getName(),label.getDesignation(),label.getDivision(),label.getTelephone(),label.getMobile(),label.getAddress(),label.getMake(),label.getModel(),label.getRelationship(),label.getRegisteredownername(),label.getRegisteredowneraddress(),label.getRegistrationnum(),label.getIcardnum(),label.getLabeltype(),label.isHiredtaxi(),label.isSignedformuploaded(),label.isEdited()});  
		  
	}
	
	@Override
	public void setremarks(int id,String remarks){
		String sql = "UPDATE labels set remarks='"+remarks+"' where id="+id;  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  
		  jdbcTemplate.update(sql);
	}
	
	@Override
	public void deleteremarks(int id){
		String sql = "UPDATE labels set remarks=NULL where id="+id;  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  
		  jdbcTemplate.update(sql);
	}
	
	@Override    
	public void signedformuploaded(int id){
		String sql = "UPDATE labels set issignedformuploaded=true where id="+id;  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  
		  jdbcTemplate.update(sql);
	}
	
	@Override    
	public void updateonexpire(){
		String sql = "update labels set labelstatus='Expired' where curdate()>enddate";  
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		  
		  jdbcTemplate.update(sql);
	}
		
	
}
