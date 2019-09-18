package com.parkinglabel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.parkinglabel.dao.LabelDocumentDao;
import com.parkinglabel.model.LabelDocument;

public class LabelDocumentServiceImpl implements LabelDocumentService {

	
	@Autowired
	LabelDocumentDao labeldocumentdao;
	
	@Override
	public List<LabelDocument> findAll() {
		return labeldocumentdao.findAll();
	}

	@Override
	public LabelDocument findById(int id) {
		return labeldocumentdao.findById(id);
	}

	@Override
	public void saveDocument(LabelDocument document) {
		labeldocumentdao.save(document);
	}

	@Override
	public List<LabelDocument> findAllByLabelId(int labelId) {
		return labeldocumentdao.findAllByLabelId(labelId);
	}

	@Override
	public void deleteById(int id) {
		labeldocumentdao.deleteById(id);
	}
	
	@Override
	public void deleteAllByLabelId(int id){
		labeldocumentdao.deleteAllByLabelId(id);
	}

}
