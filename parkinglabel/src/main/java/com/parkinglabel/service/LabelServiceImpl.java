package com.parkinglabel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.parkinglabel.dao.LabelDao;
import com.parkinglabel.model.Label;

public class LabelServiceImpl implements LabelService {

	@Autowired
	LabelDao labeldao;
	
	@Override
	public Label findById(int id) {
		return labeldao.findById(id);
	}

	@Override
	public Label findByIssueId(String issueid) {
		return labeldao.findByIssueId(issueid);
	}

	@Override
	public int saveLabel(Label label) {
		return labeldao.save(label);

	}

	@Override
	public void deleteById(int id) {
		labeldao.deleteById(id);
	}

	@Override
	public List<Label> findAllLabels() {
		return labeldao.findAllLabels();
	}

	@Override
	public List<Label> findAllByUserId(int userid) {
		return labeldao.findAllByUserId(userid);
	}

	@Override
	public void updateLabel(Label label) {
		labeldao.update(label);
	}

	@Override
	public List<Label> findAllIssueRequestLabels() {
		return labeldao.findAllIssueRequestLabels();
	}

	@Override
	public List<Label> findAllRenewRequestLabels() {
		return labeldao.findAllRenewRequestLabels();
	}

	@Override
	public int countrequests() {
		return labeldao.countrequests();
	}

	@Override
	public List<Label> search(String word, String collumn){
		return labeldao.search(word, collumn);
	}
	
	@Override
	public boolean isRegistrationNumUnique(String rn){
		return labeldao.isRegistrationNumUnique(rn);
	}

	@Override
	public void editlabel(Label label) {
		labeldao.editlabel(label);
	}

	@Override
	public void setremarks(int id, String remarks) {
		labeldao.setremarks(id, remarks);
	}

	@Override
	public void deleteremarks(int id) {
		labeldao.deleteremarks(id);
	}

	@Override
	public void signedformuploaded(int id) {
		labeldao.signedformuploaded(id);
	}
	
	@Override
	public void updateonexpire(){
		labeldao.updateonexpire();
	}
	
}
