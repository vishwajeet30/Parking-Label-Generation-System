package com.parkinglabel.dao;

import java.util.List;

import com.parkinglabel.model.Label;

public interface LabelDao {
	
	Label findById(int id);
    
    Label findByIssueId(String issueid);
     
    int save(Label label);
     
    void deleteById(int id);
     
    List<Label> findAllLabels();
    
    List<Label> findAllByUserId(int userid);
    
    List<Label> findAllIssueRequestLabels();

    List<Label> findAllRenewRequestLabels();
    
    void update(Label label);
    
    int countrequests();
    
    List<Label> search(String word, String collumn);
    
    boolean isRegistrationNumUnique(String rn);
    
    void editlabel(Label label);
    
    void setremarks(int id,String remarks);
    
    void deleteremarks(int id);
    
    void signedformuploaded(int id);
    
    void updateonexpire();
}
