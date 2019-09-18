package com.parkinglabel.dao;

import java.util.List;

import com.parkinglabel.model.LabelDocument;

public interface LabelDocumentDao {

	List<LabelDocument> findAll();
	
	LabelDocument findById(int id);
	
	void save(LabelDocument document);
	
	List<LabelDocument> findAllByLabelId(int labelId);
	
	void deleteById(int id);
	
	void deleteAllByLabelId(int id);
}
