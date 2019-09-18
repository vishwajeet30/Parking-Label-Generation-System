package com.parkinglabel.service;

import java.util.List;

import com.parkinglabel.model.Label;

public interface LabelService {

	Label findById(int id);
    
    Label findByIssueId(String issueid);
     
    int saveLabel(Label label);
     
    void deleteById(int id);
     
    List<Label> findAllLabels();
    
    List<Label> findAllByUserId(int userid);
    
    void updateLabel(Label label);
    
    List<Label> findAllIssueRequestLabels();

    List<Label> findAllRenewRequestLabels();
    
    int countrequests();
    
    List<Label> search(String word, String collumn);
    
    boolean isRegistrationNumUnique(String rn);
    
    void editlabel(Label label);
    
    void setremarks(int id,String remarks);
    
    void deleteremarks(int id);
    
    void signedformuploaded(int id);
    
    void updateonexpire();
}
